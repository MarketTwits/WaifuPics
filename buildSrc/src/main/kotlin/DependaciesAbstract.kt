abstract class DependaciesAbstract : DependenciesConfig {
    protected abstract val version: String
    protected abstract val dependence: String
    override fun fetch() = dependence + version
}