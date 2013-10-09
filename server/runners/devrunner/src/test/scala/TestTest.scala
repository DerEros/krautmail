import org.specs2.mutable.Specification
import org.mockito.Mockito._

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

  "A mocked list" should {
    "pretend to have 5 items" in {
      val listMock = mock(classOf[List[_]])
      when(listMock.length).thenReturn(5)

      listMock.length must be equalTo (5)
    }
  }
}
