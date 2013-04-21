package oo.components;

import com.webobjects.appserver.WOContext;
import com.webobjects.foundation.NSBundle;

import er.extensions.components.ERXComponent;

public class BundleDetailPage extends ERXComponent {

	private NSBundle _selectedObject;

	public BundleDetailPage( WOContext context ) {
		super( context );
	}

	public NSBundle selectedObject() {
		return _selectedObject;
	}

	public void setSelectedObject( NSBundle value ) {
		_selectedObject = value;
	}
}