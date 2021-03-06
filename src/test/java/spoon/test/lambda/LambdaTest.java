package spoon.test.lambda;

import org.junit.Before;
import org.junit.Test;
import spoon.Launcher;
import spoon.OutputType;
import spoon.compiler.SpoonCompiler;
import spoon.reflect.code.CtIf;
import spoon.reflect.code.CtLambda;
import spoon.reflect.declaration.CtMethod;
import spoon.reflect.declaration.CtParameter;
import spoon.reflect.declaration.CtType;
import spoon.reflect.factory.Factory;
import spoon.reflect.reference.CtTypeReference;
import spoon.reflect.visitor.filter.AbstractFilter;
import spoon.reflect.visitor.filter.NameFilter;
import spoon.test.TestUtils;
import spoon.test.lambda.testclasses.Foo;

import java.io.File;
import java.util.List;
import java.util.function.Predicate;

import static org.junit.Assert.*;

public class LambdaTest {
	private Factory factory;
	private CtType<Foo> foo;
	private SpoonCompiler compiler;

	@Before
	public void setUp() throws Exception {
		final Launcher launcher = new Launcher();
		this.factory = launcher.createFactory();
		factory.getEnvironment().setComplianceLevel(8);
		compiler = launcher.createCompiler(this.factory);

		compiler.setSourceOutputDirectory(TestUtils.getSpoonedDirectory(getClass()));
		compiler.setBinaryOutputDirectory(TestUtils.getBuildDirectory(getClass()));
		compiler.addInputSource(new File("./src/test/java/spoon/test/lambda/testclasses/"));
		compiler.build();
		compiler.generateProcessedSourceFiles(OutputType.COMPILATION_UNITS);

		foo = factory.Type().get(Foo.class);
	}

	@Test
	public void testLambdaExpressionWithExpressionBodyAndWithoutParameter() throws Exception {
		final CtLambda<?> lambda = getLambdaByName("lambda$1");

		assertTypedBy(Foo.Check.class, lambda.getType());
		assertParametersSizeIs(0, lambda.getParameters());
		assertHasExpressionBody(lambda);

		assertIsWellPrinted("((spoon.test.lambda.testclasses.Foo.Check)(() -> false))", lambda);
	}

	@Test
	public void testLambdaExpressionWithExpressionBodyAndWithoutTypeForParameter() throws Exception {
		final CtLambda<?> lambda = getLambdaByName("lambda$2");

		assertTypedBy(Predicate.class, lambda.getType());
		assertParametersSizeIs(1, lambda.getParameters());
		final CtParameter<?> parameter = (CtParameter<?>) lambda.getParameters().get(0);
		assertParameterTypedBy(Foo.Person.class, parameter);
		assertParameterIsNamedBy("p", parameter);
		assertHasExpressionBody(lambda);

		assertIsWellPrinted(
				"((java.util.function.Predicate<spoon.test.lambda.testclasses.Foo.Person>)((spoon.test.lambda.testclasses.Foo.Person p) -> (p.age) > 10))",
				lambda);
	}

	@Test
	public void testLambdaExpressionWithExpressionBodyAndWithMultiParameters() throws Exception {
		final CtLambda<?> lambda = getLambdaByName("lambda$3");

		assertTypedBy(Foo.CheckPersons.class, lambda.getType());
		assertParametersSizeIs(2, lambda.getParameters());
		final CtParameter<?> parameter1 = (CtParameter<?>) lambda.getParameters().get(0);
		assertParameterTypedBy(Foo.Person.class, parameter1);
		assertParameterIsNamedBy("p1", parameter1);
		final CtParameter<?> parameter2 = (CtParameter<?>) lambda.getParameters().get(1);
		assertParameterTypedBy(Foo.Person.class, parameter2);
		assertParameterIsNamedBy("p2", parameter2);
		assertHasExpressionBody(lambda);

		assertIsWellPrinted(
				"((spoon.test.lambda.testclasses.Foo.CheckPersons)((spoon.test.lambda.testclasses.Foo.Person p1,spoon.test.lambda.testclasses.Foo.Person p2) -> ((p1.age) - (p2.age)) > 0))",
				lambda);
	}

	@Test
	public void testLambdaExpressionWithExpressionBodyAndWithParameterTyped() throws Exception {
		final CtLambda<?> lambda = getLambdaByName("lambda$4");

		assertTypedBy(Predicate.class, lambda.getType());
		assertParametersSizeIs(1, lambda.getParameters());
		final CtParameter<?> parameter = (CtParameter<?>) lambda.getParameters().get(0);
		assertParameterTypedBy(Foo.Person.class, parameter);
		assertParameterIsNamedBy("p", parameter);
		assertHasExpressionBody(lambda);

		assertIsWellPrinted(
				"((java.util.function.Predicate<spoon.test.lambda.testclasses.Foo.Person>)((spoon.test.lambda.testclasses.Foo.Person p) -> (p.age) > 10))",
				lambda);
	}

	@Test
	public void testLambdaExpressionWithExpressionBodyAndWithMultiParametersTyped() throws Exception {
		final CtLambda<?> lambda = getLambdaByName("lambda$5");

		assertTypedBy(Foo.CheckPersons.class, lambda.getType());
		assertParametersSizeIs(2, lambda.getParameters());
		final CtParameter<?> parameter1 = (CtParameter<?>) lambda.getParameters().get(0);
		assertParameterTypedBy(Foo.Person.class, parameter1);
		assertParameterIsNamedBy("p1", parameter1);
		final CtParameter<?> parameter2 = (CtParameter<?>) lambda.getParameters().get(1);
		assertParameterTypedBy(Foo.Person.class, parameter2);
		assertParameterIsNamedBy("p2", parameter2);
		assertHasExpressionBody(lambda);

		assertIsWellPrinted(
				"((spoon.test.lambda.testclasses.Foo.CheckPersons)((spoon.test.lambda.testclasses.Foo.Person p1,spoon.test.lambda.testclasses.Foo.Person p2) -> ((p1.age) - (p2.age)) > 0))",
				lambda);
	}

	@Test
	public void testLambdaExpressionWithStatementBodyAndWithoutParameters() throws Exception {
		final CtLambda<?> lambda = getLambdaByName("lambda$6");

		assertTypedBy(Foo.Check.class, lambda.getType());
		assertParametersSizeIs(0, lambda.getParameters());
		assertStatementBody(lambda);

		assertIsWellPrinted("((spoon.test.lambda.testclasses.Foo.Check)(() -> {" + System.lineSeparator()
				+ "    java.lang.System.err.println(\"\");" + System.lineSeparator()
				+ "    return false;" + System.lineSeparator()
				+ "}))", lambda);
	}

	@Test
	public void testLambdaExpressionWithStatementBodyAndWithParameter() throws Exception {
		final CtLambda<?> lambda = getLambdaByName("lambda$7");

		assertTypedBy(Predicate.class, lambda.getType());
		assertParametersSizeIs(1, lambda.getParameters());
		final CtParameter<?> parameter = (CtParameter<?>) lambda.getParameters().get(0);
		assertParameterTypedBy(Foo.Person.class, parameter);
		assertParameterIsNamedBy("p", parameter);
		assertStatementBody(lambda);

		assertIsWellPrinted(
				"((java.util.function.Predicate<spoon.test.lambda.testclasses.Foo.Person>)((spoon.test.lambda.testclasses.Foo.Person p) -> {"
						+ System.lineSeparator()
						+ "    p.doSomething();" + System.lineSeparator()
						+ "    return (p.age) > 10;" + System.lineSeparator()
						+ "}))", lambda);
	}

	@Test
	public void testLambdaExpressionInIfConditional() throws Exception {
		final CtLambda<?> lambda = getLambdaByName("lambda$8");

		assertTypedBy(Predicate.class, lambda.getType());
		assertParametersSizeIs(1, lambda.getParameters());
		final CtParameter<?> parameter = (CtParameter<?>) lambda.getParameters().get(0);
		assertParameterTypedBy(Foo.Person.class, parameter);
		assertParameterIsNamedBy("p", parameter);
		assertHasExpressionBody(lambda);

		final CtMethod<?> method = foo.getElements(new NameFilter<CtMethod<?>>("m8")).get(0);
		final CtIf condition = method.getElements(new AbstractFilter<CtIf>(CtIf.class) {
			@Override
			public boolean matches(CtIf element) {
				return true;
			}
		}).get(0);
		final String expected =
				"if (((java.util.function.Predicate<spoon.test.lambda.testclasses.Foo.Person>)((spoon.test.lambda.testclasses.Foo.Person p) -> (p.age) > 18)).test(new spoon.test.lambda.testclasses.Foo.Person(10))) {"
						+ System.lineSeparator()
						+ "    java.lang.System.err.println(\"Enjoy, you have more than 18.\");" + System
						.lineSeparator()
						+ "} ";
		assertEquals("Condition must be well printed", expected, condition.toString());
	}

	@Test
	public void testCompileLambdaGeneratedBySpoon() throws Exception {
		TestUtils.canBeBuild(TestUtils.getSpoonedDirectory(getClass()), 8);
	}

	private void assertTypedBy(Class<?> expectedType, CtTypeReference<?> type) {
		assertEquals("Lambda must be typed", expectedType, type.getActualClass());
	}

	private void assertParametersSizeIs(int nbParameters, List<CtParameter<?>> parameters) {
		if (nbParameters == 0) {
			assertEquals("Lambda hasn't parameters", nbParameters, parameters.size());
		} else {
			assertEquals("Lambda has parameters", nbParameters, parameters.size());
		}
	}

	private void assertParameterTypedBy(Class<?> expectedType, CtParameter<?> parameter) {
		assertNotNull("Lambda has a parameter typed", parameter.getType());
		assertEquals("Lambda has a parameter typed by", expectedType, parameter.getType().getActualClass());
	}

	private void assertHasExpressionBody(CtLambda<?> lambda) {
		assertNotNull("Lambda has an expression for its body.", lambda.getExpression());
		assertNull("Lambda don't have a list of statements (body) for its body", lambda.getBody());
	}

	private void assertStatementBody(CtLambda<?> lambda) {
		assertNotNull("Lambda has a body with statements.", lambda.getBody());
		assertNull("Lambda don't have an expression for its body", lambda.getExpression());
	}

	private void assertParameterIsNamedBy(String name, CtParameter<?> parameter) {
		assertEquals("Lambda has a parameter with a name", name, parameter.getSimpleName());
	}

	private void assertIsWellPrinted(String expected, CtLambda<?> lambda) {
		assertEquals("Lambda must be well printed", expected, lambda.toString());
	}

	private CtLambda<?> getLambdaByName(String name) {
		return foo.getElements(new NameFilter<CtLambda<?>>(name)).get(0);
	}
}
