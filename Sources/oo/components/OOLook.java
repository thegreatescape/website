package oo.components;

import com.webobjects.appserver.WOContext;
import com.webobjects.foundation.NSArray;

import er.extensions.components.ERXComponent;

public class OOLook extends ERXComponent {

	public OOLook( WOContext context ) {
		super( context );
	}

	@Override
	public NSArray<String> additionalCSSFiles() {
		return new NSArray<String>( "bootstrap/css/bootstrap.css", "SMLook.css" );
	}

	@Override
	protected NSArray<String> additionalJavascriptFiles() {
		return new NSArray<String>( "bootstrap/js/bootstrap.js" );
	}
}