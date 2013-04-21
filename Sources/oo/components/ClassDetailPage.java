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

public class ClassDetailPage extends DetailPage<Class> {

	public boolean hidePrivate = true;

	public String currentClassName;
	public String selectedClassName;

	public NSArray<Method> methods;
	public Method currentMethod;

	public NSArray<Constructor> constructors;
	public Constructor currentConstructor;

	public NSArray<Field> fields;
	public Field currentField;

	public Class currentParameterType;

	public String classTemplate;

	public ClassDetailPage( WOContext context ) {
		super( context );
	}

	public WOActionResults look() {
		if( selectedClassName != null ) {
			ReverseEngineer e = new ReverseEngineer( selectedClassName );
			fields = e.fields( hidePrivate );
			constructors = e.constructors( hidePrivate );
			methods = e.methods( hidePrivate );
			classTemplate = e.classTemplate( hidePrivate );
		}

		return null;
	}

	public String methodModifiers() {
		return Modifier.toString( currentMethod.getModifiers() );
	}

	public String fieldModifiers() {
		return Modifier.toString( currentField.getModifiers() );
	}

	public String constructorModifiers() {
		return Modifier.toString( currentConstructor.getModifiers() );
	}

	public Object currentFieldValue() {
		if( fieldModifiers().contains( "static" ) ) {
			try {
				return currentField.get( null );
			}
			catch( IllegalArgumentException e ) {
				e.printStackTrace();
			}
			catch( IllegalAccessException e ) {
				e.printStackTrace();
			}
		}

		return null;
	}
}