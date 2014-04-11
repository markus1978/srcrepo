package de.hub.srcrepo.ocl
import org.junit.Test
import org.junit.Assert
import de.hub.srcrepo.OclCollection
import org.eclipse.emf.common.util.EList

class O;

class OclTests extends HandleCollectionConversions {
  @Test 
  def testSize() {
    val l = List(1 , 2 , 3)
    Assert.assertEquals(3, l.size())
  }
  
  @Test
  def testSelect() {
    val l = List[Int](1, 2, 3, 4, 1, 4)
    Assert.assertEquals(4, l.select((e)=>{
    	e<=3
      }).size())
  }
  
  @Test
  def testCollect() {
    val l = sListToOcl(List[Int](1, 2, 3))
    val result = l.collect((e)=>"" + e)
    Assert.assertEquals("1", result.first())
    Assert.assertEquals(3, result.size())
  }
  
  @Test
  def testCollectAll() {
    val l = List[List[Int]](List[Int](1, 2, 3), List[Int](4, 5, 6), List[Int](7, 8, 9))
    Assert.assertEquals(9, l.collectAll((e)=>e).size())
    Assert.assertEquals(21.0, l.collectAll((e)=>e).select((e)=>e<=6).sum((e)=>e), 0.01)
  }
  
  @Test
  def testCollectAllAndUseTwice() {
    val l = List[List[Int]](List[Int](1, 2, 3), List[Int](4, 5, 6), List[Int](7, 8, 9))
    val result = l.collectAll((e)=>e)
    Assert.assertEquals(9, result.size())
    Assert.assertEquals(9, result.collect((e)=>e).size())
    Assert.assertEquals(9, result.size())
  }
  
  @Test
  def testCollectAll2() {
    val o = new O();
    val l:List[Int] = List[Int](4, 3, 2, 1, 0)
    
    Assert.assertEquals(1, l.collectAll((e)=>if(e == 4) List[O](o) else List[O]()).size());
    Assert.assertEquals(1, l.collectAll((e)=>if(e == 3) List[O](o) else List[O]()).size());
    Assert.assertEquals(1, l.collectAll((e)=>if(e == 2) List[O](o) else List[O]()).size());
    Assert.assertEquals(1, l.collectAll((e)=>if(e == 1) List[O](o) else List[O]()).size());
    Assert.assertEquals(1, l.collectAll((e)=>if(e == 0) List[O](o) else List[O]()).size());
  }
  
  @Test
  def testCollectAll2AndUseTwice() {
    val o = new O();
    val l:List[Int] = List[Int](4, 3, 2, 1, 0)
    
    val result = l.collectAll((e)=>List[O](o))
    Assert.assertEquals(5, result.size());
    Assert.assertEquals(5, result.size());
  }
  
  @Test
  def testCollectAll3() {
    val o = new O()
    val l:List[Int] = List[Int](4, 3, 2, 1, 0)
    val result = l.collectAll((e)=>List[O](o))
    Assert.assertEquals(5, result.size);
  }
  
  @Test
  def testCollectAll4() {
	val o = new O();
    val l:List[Int] = List[Int](4, 3, 2, 1, 0)
    val result = l.collectAll((e)=>if(e % 2 == 0) List[O](o) else List[O]())
    Assert.assertEquals(3, result.size);
  }
  
  @Test
  def testCollectAllAndSelect() {
    val l = List[List[Int]](List[Int](1, 2, 3), List[Int](4, 5, 6), List[Int](7, 8, 9))
    Assert.assertEquals(9, l.collectAll((e)=>e).select((e)=>true).size())
  }
  
  @Test
  def testCollectAllAndSelectAndCollect() {
    val l = List[List[Int]](List[Int](1, 2, 3), List[Int](4, 5, 6), List[Int](7, 8, 9))
    Assert.assertEquals(9, l.collectAll((e)=>e).select((e)=>true).collect((e)=>e).size())
  }
  
  @Test
  def testCollectAllAndCollectAllAndSelectAndCollect() {
    val l = List[List[Int]](List[Int](1, 2, 3), List[Int](4, 5, 6), List[Int](7, 8, 9))
    Assert.assertEquals(9, l.collectAll((e)=>e).collectAll((e)=>List[Int](e)).select((e)=>true).collect((e)=>e).size())
  }
  
  @Test
  def testCollectAllDiffObjects() {
    val l = List[List[O]](List[O](new O(), new O(), new O()), List[O](new O(), new O(), new O()), List[O](new O(), new O(), new O()))
    Assert.assertEquals(9, l.collectAll((e)=>e).size())
  }
  
  @Test
  def testCollectAllSameObjects() {
    val o = new O();
    val l = List[List[O]](List[O](o, o, o), List[O](o, o, o), List[O](o, o, o))
    Assert.assertEquals(9, l.collectAll((e)=>e).size())
  }
  
  @Test
  def testSum() {
    val l = sListToOcl(List[Int](1, 2, 3))
    Assert.assertEquals(6.0, l.sum((e)=>e), 0.1)
  }
  
  @Test
  def testClosure() {
    val l:List[Int] = List[Int](1, 2)
        
    Assert.assertEquals(17, l.closure((e)=>if (e == 0) List[Int]() else List[Int](e-1,e-1,e-1)).size());
  }
}