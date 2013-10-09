import org.specs2.mutable.Specification

/**
 * User: Eros Candelaresi <eros@candelaresi.de>
 * Project: server
 * Date: 09.10.2013
 * Time: 20:36
 */
class TestTest extends Specification {
  "True" should {
    "be true" in {
      true must beTrue
    }

    "not be false" in {
      true must not be equalTo(false)
    }
  }
}
