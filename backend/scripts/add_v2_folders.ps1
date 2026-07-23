# LifeOS Backend Directory Generator Script - Version 2 Additions

$v2Folders = @(
    'docs/adr',
    'docs/diagrams',
    'docs/guides',
    'src/main/java/com/lifeos/workflow/engine',
    'src/main/java/com/lifeos/workflow/definition',
    'src/main/java/com/lifeos/rules/engine',
    'src/main/java/com/lifeos/rules/definition',
    'src/main/java/com/lifeos/observability/logging',
    'src/main/java/com/lifeos/observability/metrics',
    'src/main/java/com/lifeos/observability/tracing',
    'src/main/java/com/lifeos/observability/health',
    'src/main/java/com/lifeos/sharedkernel/domain',
    'src/main/java/com/lifeos/sharedkernel/valueobject',
    'src/main/java/com/lifeos/ai/agent',
    'src/main/java/com/lifeos/ai/tool',
    'src/main/java/com/lifeos/ai/vector',
    'src/main/java/com/lifeos/ai/orchestrator',
    'src/main/java/com/lifeos/infrastructure/database',
    'src/main/java/com/lifeos/infrastructure/cache',
    'src/main/java/com/lifeos/infrastructure/messaging',
    'src/main/java/com/lifeos/infrastructure/storage'
)

foreach ($path in $v2Folders) {
    if (-not (Test-Path -Path $path)) {
        New-Item -ItemType Directory -Force -Path $path | Out-Null
        New-Item -ItemType File -Force -Path "$path/.gitkeep" | Out-Null
    }
}

Write-Host "Version 2 folder additions created successfully."
