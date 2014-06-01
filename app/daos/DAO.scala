package daos

import play.api.db.DB

trait DAO {

  import play.api.Play.current
  import play.api.db.slick.Config.driver.simple._

  lazy val db = Database.forDataSource(DB.getDataSource("default"))

}
