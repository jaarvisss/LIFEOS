# LifeOS Backend Directory Generator Script

$modules = @(
    'auth', 'user', 'profile', 'people', 'relationship', 
    'calendar', 'event', 'task', 'goal', 'project', 
    'finance', 'document', 'knowledge', 'health', 
    'notification', 'scheduler', 'analytics', 'audit', 'search'
)

$subfolders = @(
    'controller', 'service', 'repository', 'entity', 
    'dto', 'mapper', 'validator', 'exception', 
    'event', 'config', 'util', 'specification', 'client'
)

foreach ($m in $modules) {
    foreach ($s in $subfolders) {
        $path = "src/main/java/com/lifeos/module/$m/$s"
        if (-not (Test-Path -Path $path)) {
            New-Item -ItemType Directory -Force -Path $path | Out-Null
            New-Item -ItemType File -Force -Path "$path/.gitkeep" | Out-Null
        }
    }
}

# Create test subdirectories
$testPackages = @(
    'common', 'integration', 'security', 'ai', 'config'
)

foreach ($t in $testPackages) {
    $path = "src/test/java/com/lifeos/$t"
    if (-not (Test-Path -Path $path)) {
        New-Item -ItemType Directory -Force -Path $path | Out-Null
        New-Item -ItemType File -Force -Path "$path/.gitkeep" | Out-Null
    }
}

foreach ($m in $modules) {
    $path = "src/test/java/com/lifeos/module/$m"
    if (-not (Test-Path -Path $path)) {
        New-Item -ItemType Directory -Force -Path $path | Out-Null
        New-Item -ItemType File -Force -Path "$path/.gitkeep" | Out-Null
    }
}

Write-Host "Directory structure generation completed successfully."
