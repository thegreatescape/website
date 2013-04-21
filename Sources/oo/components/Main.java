package oo.components;

import com.webobjects.appserver.WOActionResults;
import com.webobjects.appserver.WOContext;
import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSBundle;

import er.extensions.components.ERXComponent;

public class Main extends ERXComponent {

	public String currentBundleName;

	public Main( WOContext context ) {
		super( context );
	}

	private NSBundle selectedBundle() {
		if( currentBundleName != null ) {
			return NSBundle.bundleForName( currentBundleName );
		}

		return null;
	}

	public WOActionResults lookAtBundle() {
		BundleDetailPage p = pageWithName( BundleDetailPage.class );
		p.setSelectedObject( selectedBundle() );
		return p;
	}

	public NSArray<String> bundleNames() {
		return new NSArray<>( "JavaFoundation", "JavaWebObjects" );
	}
}