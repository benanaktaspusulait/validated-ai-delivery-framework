async function loadHistory() {
    var res = await fetch('/api/v1/drift/history');
    var data = await res.json();
    document.getElementById('totalCount').textContent = data.length;
    var detected = 0;
    for (var i = 0; i < data.length; i++) {
        if (data[i].driftDetected) detected++;
    }
    document.getElementById('detectedCount').textContent = detected;
    var tbody = document.getElementById('historyTable');
    if (data.length === 0) {
        tbody.innerHTML = '<tr><td colspan="5" class="empty-state">No drift checks yet.</td></tr>';
        return;
    }
    var html = '';
    for (var i = 0; i < data.length; i++) {
        var h = data[i];
        html += '<tr><td>' + h.checkedAt.substring(0,16).replace('T',' ') + '</td><td><code>' + h.modelName + '</code></td><td>' + h.psiScore.toFixed(4) + '</td><td><span class="status-badge status-' + h.severity.toLowerCase() + '">' + h.severity + '</span></td><td>' + (h.driftDetected ? 'YES' : 'NO') + '</td></tr>';
    }
    tbody.innerHTML = html;
}
document.getElementById('driftForm').addEventListener('submit', async function(e) {
    e.preventDefault();
    var modelName = document.getElementById('modelName').value;
    var reference = document.getElementById('reference').value.split(',').map(Number);
    var current = document.getElementById('current').value.split(',').map(Number);
    var res = await fetch('/api/v1/drift/check/' + encodeURIComponent(modelName), {
        method: 'POST', headers: {'Content-Type': 'application/json'},
        body: JSON.stringify({ reference: reference, current: current })
    });
    var result = await res.json();
    document.getElementById('resultSection').style.display = 'block';
    document.getElementById('driftDetected').textContent = result.driftDetected ? 'YES' : 'NO';
    document.getElementById('driftDetected').className = 'result-value ' + (result.driftDetected ? 'text-danger' : 'text-success');
    document.getElementById('psiScore').textContent = result.overallScore.toFixed(4);
    document.getElementById('severity').textContent = result.severity;
    setTimeout(function() { loadHistory(); }, 500);
});
loadHistory();
