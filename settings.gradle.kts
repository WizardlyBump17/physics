rootProject.name = "physics"

include("two")

include("graphics")
include("graphics:graphics-two")
findProject("graphics:graphics-two")?.name = "graphics-two"