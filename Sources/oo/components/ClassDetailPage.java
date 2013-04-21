package oo.components;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import oo.ReverseEngineer;

import com.webobjects.appserver.WOActionResults;
import com.webobjects.appserver.WOContext;
import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSBundle;
import com.webobjects.foundation.NSComparator;
import com.webobjects.foundation.NSComparator.ComparisonException;

import er.extensions.components.ERXComponent;

public class ClassDetailPage extends ERXComponent {

	public boolean hidePrivate = true;

	public int index;

	public String currentBundleName;
	public String selectedBundleName;

	public String currentClassName;
	public String selectedClassName;

	public NSArray<Method> methods;
	public Method currentMethod;

	public NSArray<Constructor> constructors;
	public Constructor currentConstructor;

	public NSArray<Field> fields;
	public Field currentField;

	public Class currentParameterType;

	public String string;

	public ClassDetailPage( WOContext context ) {
		super( context );
	}

	public WOActionResults lookAtBundle() {

	}

	public WOActionResults look() {
		if( selectedClassName != null ) {
			ReverseEngineer e = new ReverseEngineer( selectedClassName );
			fields = e.fields( hidePrivate );
			constructors = e.constructors( hidePrivate );
			methods = e.methods( hidePrivate );
			string = e.classBody( hidePrivate );
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

	public NSArray<String> bundleNames() {
		return new NSArray<>( "JavaFoundation", "JavaWebObjects" );
	}

	public Object currentFieldValue() {
		if( fieldModifiers().contains( "static" ) ) {
			try {
				return currentField.get( null );
			}
			catch( IllegalArgumentException e ) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			catch( IllegalAccessException e ) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return null;
	}

	public NSArray<String> classNames() {
		NSArray<String> a = NSBundle.bundleForName( selectedBundleName ).bundleClassNames();

		try {
			a = a.sortedArrayUsingComparator( NSComparator.AscendingCaseInsensitiveStringComparator );
		}
		catch( ComparisonException e ) {
			e.printStackTrace();
		}

		return a;
	}
}