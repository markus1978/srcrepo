package testclasses;

public class CboTypeCouplingTest {

	public CkDitTest returnNonPrimitive(){
		return new CkDitTest();
	}
	
	public CkDitTest returnNonPrimitiveWithArgs(CkLcomTest parameter){
		return new CkDitTest();
	}

}
