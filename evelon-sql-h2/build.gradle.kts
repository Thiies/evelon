dependencies {
    api(libs.hikari)
    
    implementation(libs.h2)
    api(project(":evelon-common"))
    api(project(":evelon-sql-parent"))
}