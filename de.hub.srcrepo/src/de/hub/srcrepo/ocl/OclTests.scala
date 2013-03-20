package de.hub.srcrepo.ocl
import org.junit.Test
import org.junit.Assert
import de.hub.srcrepo.OclCollection
import org.eclipse.emf.common.util.EList

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
    Assert.assertEquals(3, l.collect((e)=>'c').size())
    Assert.assertEquals('c', l.collect((e)=>'c').first())
  }
  
  @Test
  def testCollectAll() {
    val l = List[List[Int]](List[Int](1, 2, 3), List[Int](4, 5, 6), List[Int](7, 8, 9))
    Assert.assertEquals(9, l.collectAll((e)=>e).size())
    Assert.assertEquals(21.0, l.collectAll((e)=>e).select((e)=>e<=6).sum((e)=>e), 0.01)
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