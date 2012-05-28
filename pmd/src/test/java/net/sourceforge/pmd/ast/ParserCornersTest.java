package net.sourceforge.pmd.ast;

import net.sourceforge.pmd.PMD;
import net.sourceforge.pmd.testframework.ParserTst;

import org.junit.Ignore;
import org.junit.Test;


public class ParserCornersTest extends ParserTst {

    @Test
    public final void testGetFirstASTNameImageNull() throws Throwable {
        parseJava14(ABSTRACT_METHOD_LEVEL_CLASS_DECL);
    }

    @Test
    @Ignore
    public final void testCastLookaheadProblem() throws Throwable {
        parseJava14(CAST_LOOKAHEAD_PROBLEM);
    }
    
    /**
     * Tests a specific generic notation for calling methods.
     * See: https://jira.codehaus.org/browse/MPMD-139
     */
    @Test
    @Ignore
    public void testGenericsProblem() {
    	parseJava15(GENERICS_PROBLEM);
    	parseJava17(GENERICS_PROBLEM);
    }
    
    private static final String GENERICS_PROBLEM =
    		"public class Test {" + PMD.EOL +
    		" public void test() {" + PMD.EOL +
    		"   String o = super.<String> doStuff(\"\");" + PMD.EOL +
    		" }" + PMD.EOL +
    		"}";

    private static final String ABSTRACT_METHOD_LEVEL_CLASS_DECL =
            "public class Test {" + PMD.EOL +
            "  void bar() {" + PMD.EOL +
            "   abstract class X { public abstract void f(); }" + PMD.EOL +
            "   class Y extends X { public void f() {" + PMD.EOL +
            "    new Y().f();" + PMD.EOL +
            "   }}" + PMD.EOL +
            "  }" + PMD.EOL +
            "}";

    private static final String CAST_LOOKAHEAD_PROBLEM =
        "public class BadClass {" + PMD.EOL +
        "  public Class foo() {" + PMD.EOL +
        "    return (byte[].class);" + PMD.EOL +
        "  }" + PMD.EOL +
        "}";

    public static junit.framework.Test suite() {
        return new junit.framework.JUnit4TestAdapter(ParserCornersTest.class);
    }
}
