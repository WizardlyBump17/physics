rootProject.name = "physics"

include("shared")

include("two")
include("three")

include("graphics")
include("graphics:graphics-two")
findProject("graphics:graphics-two")?.name = "graphics-two"
