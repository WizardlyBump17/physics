val jetbrainsAnnotations = "26.0.1"

dependencies {
    implementation("org.jetbrains:annotations:${jetbrainsAnnotations}")
    implementation(project(":two"))
    implementation(project(":shared"))
}