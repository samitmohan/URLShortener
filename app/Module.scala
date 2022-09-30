import com.google.inject.AbstractModule
import repository.{RedisUrlRepository, UrlRepository}

// Binds redis and url-repo as one instance
class Module extends AbstractModule {
  override def configure(): Unit = {
    bind(classOf[UrlRepository]).to(classOf[RedisUrlRepository]).asEagerSingleton()
  }
}