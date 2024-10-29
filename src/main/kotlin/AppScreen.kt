enum class AppScreen {

    Main,
    Detail,

    ;

    fun withArgs(vararg args: String): String {
        return buildString {
            append(name)
            args.forEach { arg -> append("/$arg") }
        }
    }

}