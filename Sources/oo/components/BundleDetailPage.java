package oo.components;

import oo.DetailPage;

import com.webobjects.appserver.WOActionResults;
import com.webobjects.appserver.WOContext;
import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSBundle;
import com.webobjects.foundation.NSComparator;
import com.webobjects.foundation.NSComparator.ComparisonException;

public class BundleDetailPage extends DetailPage<NSBundle> {

	public String currentClassName;

	public BundleDetailPage( WOContext context ) {
		super( context );
	}

	public NSArray<String> classNames() {
		NSArray<String> a = selectedObject().bundleClassNames();

		try {
			a = a.sortedArrayUsingComparator( NSComparator.AscendingStringComparator );
		}
		catch( ComparisonException e ) {
			e.printStackTrace();
		}

		return a;
	}

	public WOActionResults selectObject() {
		ClassDetailPage p = pageWithName( ClassDetailPage.class );
		p.setSelectedObject( currentClassName );
		return p;
	}

	public String currentPackageName() {
		int lastPeriod = currentClassName.lastIndexOf( '.' );

		if( lastPeriod == -1 ) {
			return null;
		}

		return currentClassName.substring( 0, lastPeriod );
	}

	public String currentSimpleClassName() {
		int lastPeriod = currentClassName.lastIndexOf( '.' );
		return currentClassName.substring( lastPeriod + 1, currentClassName.length() );
	}
}