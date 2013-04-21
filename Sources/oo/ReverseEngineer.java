package oo;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.net.URLClassLoader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSMutableArray;

/**
 * Allows us to take a little look at a class's insides using reflection.
 */

public class ReverseEngineer {

	private static final Logger logger = LoggerFactory.getLogger( ReverseEngineer.class );

	private static File JAR_FILE = new File( "/Users/hugi/Desktop/oo/projects/oo-foundation/target/oo-foundation.jar" );
	private static URLClassLoader _classLoader;

	private static Class forName( String className ) {

		logger.info( "Loading class with name: " + className );

		try {
			if( _classLoader == null ) {
				URL fileURL = JAR_FILE.toURI().toURL();
				String jarURL = "jar:" + fileURL + "!/";
				URL urls[] = {
					new URL( jarURL )
				};
				_classLoader = new URLClassLoader( urls );
			}

			return Class.forName( className, true, _classLoader );
		}
		catch( Exception e ) {
			throw new RuntimeException( e );
		}
	}

	/**
	 * The class we're wrapping/looking at.
	 */
	private Class clazz;

	/**
	 * Construct a new reverse engineer for the named class.
	 */
	public ReverseEngineer( String className ) {
		clazz = forName( className );
	}

	public NSArray<Field> fields( boolean hidePrivate ) {
		NSMutableArray<Field> results = new NSMutableArray<>();

		for( Field current : clazz.getDeclaredFields() ) {
			if( hidePrivate ) {
				String modifiers = Modifier.toString( current.getModifiers() );

				if( !modifiers.contains( "private" ) ) {
					results.addObject( current );
				}
			}
			else {
				results.addObject( current );
			}
		}

		return results;
	}

	public NSArray<Constructor> constructors( boolean hidePrivate ) {
		NSMutableArray<Constructor> results = new NSMutableArray<>();

		for( Constructor current : clazz.getDeclaredConstructors() ) {
			if( hidePrivate ) {
				String modifiers = Modifier.toString( current.getModifiers() );

				if( !modifiers.contains( "private" ) ) {
					results.addObject( current );
				}
			}
			else {
				results.addObject( current );
			}
		}

		return results;
	}

	public NSArray<Method> methods( boolean hidePrivate ) {
		NSMutableArray<Method> results = new NSMutableArray<>();

		for( Method current : clazz.getDeclaredMethods() ) {
			if( hidePrivate ) {
				String modifiers = Modifier.toString( current.getModifiers() );

				if( !modifiers.contains( "private" ) ) {
					results.addObject( current );
				}
			}
			else {
				results.addObject( current );
			}
		}

		return results;
	}

	public String classBody( boolean hidePrivate ) {
		StringBuilder b = new StringBuilder();

		b.append( "package " );
		b.append( clazz.getPackage().getName() );
		b.append( ";\n\n" );

		String classModifierString = Modifier.toString( clazz.getModifiers() );

		if( classModifierString != null && !classModifierString.isEmpty() ) {
			b.append( classModifierString );
			b.append( " " );
		}

		if( !clazz.isInterface() ) {
			b.append( "class " );
		}

		b.append( clazz.getSimpleName() );
		b.append( " {" );
		b.append( "\n\n" );

		for( Field current : fields( hidePrivate ) ) {
			b.append( "\t" );

			String modifierString = Modifier.toString( current.getModifiers() );

			if( modifierString != null && !modifierString.isEmpty() ) {
				b.append( modifierString );
				b.append( " " );
			}

			b.append( current.getType().getSimpleName() );
			b.append( " " );
			b.append( current.getName() );
			b.append( ";\n" );
		}

		b.append( "\n" );

		for( Constructor current : constructors( hidePrivate ) ) {
			b.append( "\t" );

			String modifierString = Modifier.toString( current.getModifiers() );

			if( modifierString != null && !modifierString.isEmpty() ) {
				b.append( modifierString );
				b.append( " " );
			}

			b.append( clazz.getSimpleName() );
			b.append( "( " );

			int i = 0;

			for( Class parameterType : current.getParameterTypes() ) {
				if( i++ > 0 ) {
					b.append( ", " );
				}

				b.append( parameterType.getSimpleName() );
				b.append( " " );
				b.append( parameterType.getSimpleName().toLowerCase() );
				b.append( i );
			}

			b.append( " ) {}\n\n" );
		}

		for( Method current : methods( hidePrivate ) ) {
			b.append( "\t" );

			String modifierString = Modifier.toString( current.getModifiers() );

			if( modifierString != null && !modifierString.isEmpty() ) {
				b.append( modifierString );
				b.append( " " );
			}

			b.append( current.getReturnType().getSimpleName() );
			b.append( " " );

			b.append( current.getName() );
			b.append( "( " );

			int i = 0;

			for( Class parameterType : current.getParameterTypes() ) {
				if( i++ > 0 ) {
					b.append( ", " );
				}

				b.append( parameterType.getSimpleName() );
				b.append( " " );
				b.append( parameterType.getSimpleName().toLowerCase() );
				b.append( i );
			}

			b.append( " ) {\n\t\tthrow new NotImplementedException();\n\t}\n\n" );
		}

		b.append( "}" );

		return b.toString();
	}
}