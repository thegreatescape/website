package oo.components;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import oo.DetailPage;
import oo.ReverseEngineer;

import com.webobjects.appserver.WOActionResults;
import com.webobjects.appserver.WOContext;
import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSBundle;

public class ClassDetailPage extends DetailPage<String> {

	public boolean hidePrivate = true;

	public Method currentMethod;
	public Constructor currentConstructor;
	public Field currentField;
	public Class currentParameterType;
	private ReverseEngineer _reverseEngineer;

	public ClassDetailPage( WOContext context ) {
		super( context );
	}

	@Override
	public void setSelectedObject( String object ) {
		super.setSelectedObject( object );
		_reverseEngineer = new ReverseEngineer( object );
	}

	public NSArray<Field> fields() {
		return _reverseEngineer.fields( hidePrivate );
	}

	public NSArray<Constructor> constructors() {
		return _reverseEngineer.constructors( hidePrivate );
	}


	public NSArray<Method> methods() {
		return _reverseEngineer.methods( hidePrivate );
	}

	public String classTemplate() {
		return _reverseEngineer.classTemplate( hidePrivate );
	}

	public String fieldModifiers() {
		return Modifier.toString( currentField.getModifiers() );
	}

	public String constructorModifiers() {
		return Modifier.toString( currentConstructor.getModifiers() );
	}

	public String methodModifiers() {
		return Modifier.toString( currentMethod.getModifiers() );
	}

	public Object currentFieldValue() {
		if( fieldModifiers().contains( "static" ) ) {
			try {
				return currentField.get( null );
			}
			catch( IllegalArgumentException e ) {
				System.out.println( currentField );
				e.printStackTrace();
			}
			catch( IllegalAccessException e ) {
				System.out.println( currentField );
				e.printStackTrace();
			}
		}

		return null;
	}

	public NSBundle bundle() {
		try {
			return NSBundle.bundleForClass( Class.forName( selectedObject() ) );
		}
		catch( ClassNotFoundException e ) {
			e.printStackTrace();
			return null;
		}
	}

	public WOActionResults selectBundle() {
		BundleDetailPage p = pageWithName( BundleDetailPage.class );
		p.setSelectedObject( bundle() );
		return p;
	}

}