import static org.junit.Assert.*;

import org.junit.Test;


public class GameTest {

	@Test
	public void test() {
		Game map = new Game();
		Game.FileRead();
		assertEquals("[[#, #, #, #, #, #], [#, .,  , *, R, #], [#,  ,  , x, ., #], [#, x,  , *,  , #], [L,  ,  , ., x, #], [#, #, #, #, #, #]]", map.getMap());
		assertEquals(3,map.getDiamond());
	}

}
