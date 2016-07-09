package exceptions

class NuclearExplosionException (message: Option[String] = None, cause: Option[Throwable] = None) extends NotSoDangerousException (message, cause)
