package daos

import play.api.db.DB
import play.api.db.slick.Config.driver.simple._

trait DAO {

  import play.api.Play.current

  lazy val db = Database.forDataSource(DB.getDataSource("default"))

}
