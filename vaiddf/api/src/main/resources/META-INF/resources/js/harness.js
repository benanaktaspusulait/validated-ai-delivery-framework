async function loadHistory() {
    var res = await fetch('/api/v1/harness-maturity');
    var data = await res.json();
    var tbody = document.getElementById('historyTable');
    if (data.length === 0) return;
    var html = '';
    for (var i = 0; i < data.length; i++) {
        var a = data[i];
        html += '<tr><td>' + a.assessedAt.substring(0,10) + '</td><td><code>' + a.repositoryName + '</code></td><td><span class="status-badge status-' + a.level.toLowerCase() + '">' + a.level + '</span></td><td>' + a.numericScore + '/4</td><td>' + (a.coverage * 100).toFixed(0) + '%</td></tr>';
    }
    tbody.innerHTML = html;
    var latest = data[0];
    document.getElementById('latestScore').textContent = latest.numericScore + '/4';
    document.getElementById('latestLevel').textContent = latest.level;
    document.getElementById('coverage').textContent = (latest.coverage * 100).toFixed(0) + '%';
    document.getElementById('recCount').textContent = latest.recommendations.length;
}
document.getElementById('assessForm').addEventListener('submit', async function(e) {
    e.preventDefault();
    var res = await fetch('/api/v1/harness-maturity/assess', {
        method: 'POST', headers: {'Content-Type': 'application/json'},
        body: JSON.stringify({ repositoryName: document.getElementById('repoName').value, repositoryPath: document.getElementById('repoPath').value })
    });
    if (!res.ok) { alert('Assessment failed'); return; }
    var r = await res.json();
    document.getElementById('resultSection').style.display = 'block';
    document.getElementById('recSection').style.display = 'block';
    var evidenceHtml = '';
    for (var i = 0; i < r.evidence.length; i++) {
        var e = r.evidence[i];
        evidenceHtml += '<tr><td>' + e.dimension + '</td><td>' + (e.present ? '✓' : '✗') + '</td><td>' + e.confidence + '</td><td>' + e.evidenceSummary + '</td></tr>';
    }
    document.getElementById('evidenceTable').innerHTML = evidenceHtml;
    var recHtml = '';
    for (var i = 0; i < r.recommendations.length; i++) {
        var rec = r.recommendations[i];
        recHtml += '<tr><td>' + rec.dimension + '</td><td>' + rec.action + '</td><td>' + rec.priority + '</td></tr>';
    }
    document.getElementById('recTable').innerHTML = recHtml;
    loadHistory();
});
loadHistory();
