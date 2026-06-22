# Roadmap: Doküman Ürününden Çalışan Ürüne Geçiş

**Tek Cümlelik Vaat:** `AI Delivery Control Plane for Regulated Java Enterprises`

**Versiyon:** 1.0 | **Tarih:** 2026-06-22 | **Süre:** 90 gün (12 hafta)

---

## Executive Summary

Bu doküman, mevcut `ai-project-plan` reposunun "doküman + yarı-Java/yarı-Python karışık" yapısından, **çalışan, Java-only, regülasyon-uyumlu bir ürüne** dönüşümünü tanımlar.

**Mevcut Durum:**
- 30+ markdown dokümanı, kod az
- Python: `drift/monitor.py`, `mlflow/train.py`, `streamlit/app.py`
- Java: Quarkus API (kısmen çalışıyor)
- Dağınık yapı, net ürün tanımı yok

**Hedef Durum:**
- Maven multi-module monorepo
- Java 21 + Quarkus 3.x
- Annotation-based governance (`@ModelGoverned`, `@DriftMonitored`)
- CLI (picocli), Web UI, Policy Engine
- 5 dakikada çalışan Docker Compose demo

---

## Fazlar ve Haftalık Planlama

### Faz 0: Temizlik ve Kararlar (Hafta 1-2)

**Hedef:** Teknik borçları temizle, Java-only kararını uygula, monorepo yapısını kur.

| Görev | Hafta | Çıktı |
|-------|-------|-------|
| Python dosyalarını `legacy/` altına taşı | 1 | `legacy/` dizini, README'de "deprecated" notu |
| Maven multi-module yapısını oluştur | 1 | Parent pom.xml + modüller |
| `core` modülü: annotation'lar + SPI | 1-2 | `@ModelGoverned`, `@DriftMonitored`, `@FairnessChecked` |
| `api` modülünü mevcut Quarkus kodunu yeniden yapılandır | 2 | `com.vaiddf.api` package |
| CI pipeline'ı kur (GitHub Actions) | 2 | Build + Test + Trivy scan |
| README'yi ürün dokümanına çevir | 2 | Tek cümlelik vaat + quickstart |

**Kilometre Taşı:** `mvn clean install` tüm modülleri derliyor, CI yeşil.

---

### Faz 1: Core Platform (Hafta 3-6)

**Hedef:** Model lifecycle yönetimi için temel API'ler ve governance annotasyonları.

| Görev | Hafta | Çıktı |
|-------|-------|-------|
| **Core Modülü** | | |
| Model metadata domain modeli | 3 | `Model`, `ModelVersion`, `ModelArtifact` record'ları |
| Model Registry SPI | 3 | `ModelRegistry` arayüzü + in-memory impl |
| Governance annotation'ları | 3-4 | `@ModelGoverned`, `@DriftMonitored`, `@FairnessChecked` |
| Annotation processor (compile-time) | 4 | Validation rules, metadata extraction |
| Runtime interceptor (CDI) | 4-5 | Annotation'ları runtime'da yorumlayan interceptor'lar |
| **API Modülü** | | |
| Model Registry REST API | 5 | CRUD endpoint'leri + OpenAPI spec |
| Model deployment endpoint | 5-6 | `/models/{id}/deploy`, `/models/{id}/rollback` |
| Audit log servisi | 6 | Immutable audit trail (EU AI Act uyumlu) |
| **Drift Extension** | | |
| Drift monitoring SPI + Java impl | 5-6 | PSI hesaplama, Apache Commons Math |
| Drift detection REST API | 6 | `/drift/check`, `/drift/history` |

**Kilometre Taşı:** `curl` ile model deploy edilebiliyor, drift kontrolü çalışıyor, audit log yazıyor.

---

### Faz 2: Policy Engine ve CLI (Hafta 7-10)

**Hedef:** Karar verme mekanizması ve geliştirici deneyimi.

| Görev | Hafta | Çıktı |
|-------|-------|-------|
| **Policy Engine** | | |
| Policy DSL tanımlama | 7 | Java DSL + JSON policy formatı |
| Policy evaluation engine | 7-8 | `"Bu model prod'a çıkabilir mi?"` kararı |
| OPA/Rego entegrasyonu (opsiyonel) | 8 | External policy provider SPI |
| Policy REST API | 8 | `/policies`, `/policies/evaluate` |
| **CLI** | | |
| Picocli iskeleti | 8 | `vaiddf` komutu |
| `vaiddf init` — proje iskeleti | 9 | Maven projesi oluşturur |
| `vaiddf deploy` — model deploy | 9 | API'yi çağırır |
| `vaiddf audit` — audit log sorgusu | 9 | Audit trail gösterir |
| `vaiddf policy` — policy yönetimi | 10 | Policy CRUD + evaluate |
| **Multi-tenancy** | | |
| Tenant context + Keycloak SPI | 9-10 | RBAC, JWT validation |

**Kilometre Taşı:** `vaiddf deploy --model credit-risk-v2 --env prod` komutu çalışıyor, policy engine karar veriyor.

---

### Faz 3: Web UI ve Demo (Hafta 11-12)

**Hedef:** Görünür ürün, 5 dakikada çalışan demo.

| Görev | Hafta | Çıktı |
|-------|-------|-------|
| **Web UI** | | |
| Model Registry sayfası | 11 | Model listesi, versiyon detayı |
| Drift Dashboard | 11 | Drift grafikleri, trend |
| Policy Dashboard | 11-12 | Policy listesi, evaluation sonuçları |
| Audit Log viewer | 12 | Filtrelenebilir audit trail |
| **Demo** | | |
| Docker Compose quickstart | 11 | `docker compose up` ile çalışan demo |
| Credit risk model örneği | 11-12 | End-to-end: train → deploy → drift → rollback |
| Landing page | 12 | Tek sayfa ürün tanıtımı |

**Kilometre Taşı:** `docker compose up` → 5 dakikada çalışan demo, dashboard görünüyor.

---

## Mimari Kararlar

### 1. Maven Multi-Module Yapısı

```
vaiddf/
├── pom.xml                    (parent)
├── core/                      (domain model, annotations, SPI)
├── api/                       (REST API, Quarkus)
├── cli/                       (picocli CLI)
├── ui/                        (Quarkus + Qute frontend)
├── policy-engine/             (policy evaluation)
├── extensions/
│   ├── drift/                 (drift monitoring)
│   ├── fairness/              (fairness checking)
│   └── explainability/        (SHAP/LIME wrapper)
├── dist/                      (assembly, Docker images)
└── legacy/                    (eski Python kodları)
```

### 2. Tech Stack

| Katman | Teknoloji | Gerekçe |
|--------|-----------|---------|
| Language | Java 21 (LTS) | Record, pattern matching, virtual threads |
| Framework | Quarkus 3.x | Fast startup, native build, Kubernetes-native |
| Build | Maven 3.9 + Wrapper | Reproducible build |
| CLI | Picocli | Java-native CLI framework |
| DB | PostgreSQL + Testcontainers | Audit log, model registry |
| Policy | Java DSL + OPA (opsiyonel) | Esnek policy tanımlama |
| UI | Qute (server-side) + HTMX | Hafif, Quarkus native |
| Auth | Keycloak | RBAC, multi-tenancy |
| CI | GitHub Actions | Build + Test + Scan |
| Container | Jib / Docker | Image build |
| Scan | Trivy + CycloneDX | Güvenlik + SBOM |

### 3. Annotation-Driven Governance

```java
@ModelGoverned(
    registry = "credit-risk-registry",
    driftCheck = true,
    fairnessRequired = true,
    maxDriftPSI = 0.2,
    policy = "credit-risk-prod-policy"
)
@Path("/predict")
public class CreditRiskPredictor {
    // runtime'da governance kuralları uygulanır
}
```

### 4. Plugin SPI

```java
public interface DriftDetector {
    String name();
    DriftResult check(double[] reference, double[] current);
    Map<String, Object> metrics();
}

// Kullanıcı kendi detector'ını ekler:
@ApplicationScoped
@DriftDetectorSelector("psi")
public class PSIDetector implements DriftDetector { ... }
```

---

## Kritik Başarı Faktörleri

### 1. Python'u Tamamen Sil (veya İzole Et)
- `legacy/` altına taşı, README'de "deprecated" olarak işaretle
- Hiçbir yeni özellik Python'da yazılmamalı
- ML training için MLflow REST API'sine Java client (zaten var)

### 2. Doküman Değil Kod Ürünü
- Her prensip annotation/SPI olarak yaşamalı
- `docs/` sadece referans olmalı, ürünün kendisi kod olmalı
- OpenAPI spec = ürünün contract'ı

### 3. Çalışan Demo
- `docker compose up` → 5 dakikada çalışan örnek
- Credit risk modeli → deploy → drift simülasyonu → dashboard
- Bu, satış ve demo için en güçlü araç

---

## Riskler ve Azaltma

| Risk | Olasılık | Etki | Azaltma |
|------|----------|------|---------|
| Quarkus öğrenme eğrisi | Orta | Yüksek | Mevcut API'yi temel al, incrementally genişlet |
| MLflow Java client eksiklikleri | Düşük | Orta | REST API wrapper yaz, custom client |
| UI karmaşıklığı | Orta | Orta | Qute + HTMX ile basit başla, Later React'e geç |
| Policy engine kapsamı | Yüksek | Yüksek | Faz 1'de basit DSL, Faz 2'de OPA ekle |
| Multi-tenancy复杂度 | Orta | Yüksek | Keycloak ile başla, custom auth yazma |

---

## Ölçüm Kriterleri

### Haftalık
- [ ] `mvn clean install` başarılı mı?
- [ ] CI pipeline yeşil mi?
- [ ] Yeni annotation/test eklendi mi?

### Aylık
- [ ] Çalışan API endpoint sayısı
- [ ] Test coverage (>80% hedef)
- [ ] Docker Compose demo çalışıyor mu?

### 90. Gün
- [ ] 3+ annotation çalışıyor mu?
- [ ] CLI 3+ komut sunuyor mu?
- [ ] Web UI 3+ sayfa gösteriyor mu?
- [ ] Docker Compose demo 5 dakikada çalışıyor mu?
- [ ] EU AI Act madde eşleme tablosu hazır mı?

---

## Sonraki Adımlar

1. Bu dokümanı ekibinle paylaş
2. Faz 0'a başla (Python'u legacy'ye taşı)
3. Haftalık sprint planlaması yap
4. İlk 2 hafta sonunda checkpoint: "mvn clean install çalışıyor mu?"

---

*Bu doküman living document'dır. Her sprint sonunda güncellenir.*
