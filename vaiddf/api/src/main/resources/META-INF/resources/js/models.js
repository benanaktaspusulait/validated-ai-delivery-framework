async function loadModels() {
    const res = await fetch('/api/v1/models');
    const data = await res.json();
    document.getElementById('modelCount').textContent = data.length + ' models';
    const tbody = document.getElementById('modelTable');
    if (data.length === 0) {
        tbody.innerHTML = '<tr><td colspan="6" class="empty-state">No models registered. Use POST /api/v1/models to create one.</td></tr>';
        return;
    }
    var html = '';
    for (var i = 0; i < data.length; i++) {
        var m = data[i];
        var actions = '';
        if (m.status === 'REGISTERED') {
            actions = '<button class="btn btn-sm btn-primary" onclick="deploy(\'' + m.id + '\')">Deploy</button>';
        } else if (m.status === 'DEPLOYED') {
            actions = '<button class="btn btn-sm btn-danger" onclick="rollback(\'' + m.id + '\')">Rollback</button>';
        }
        html += '<tr><td><code>' + m.id + '</code></td><td>' + m.name + '</td><td>' + m.version + '</td><td><span class="status-badge status-' + m.status.toLowerCase() + '">' + m.status + '</span></td><td>' + m.registry + '</td><td>' + actions + '</td></tr>';
    }
    tbody.innerHTML = html;
}
async function deploy(id) {
    if (confirm('Deploy model ' + id + '?')) {
        await fetch('/api/v1/models/' + id + '/deploy', { method: 'POST' });
        loadModels();
    }
}
async function rollback(id) {
    if (confirm('Rollback model ' + id + '?')) {
        await fetch('/api/v1/models/' + id + '/rollback', { method: 'POST' });
        loadModels();
    }
}
loadModels();
