package oo;

import com.webobjects.appserver.WOContext;

import er.extensions.components.ERXComponent;

public abstract class DetailPage<E> extends ERXComponent {

	private E _selectedObject;

	public DetailPage( WOContext context ) {
		super( context );
	}

	public E selectedObject() {
		return _selectedObject;
	}

	public void setSelectedObject( E object ) {
		_selectedObject = object;
	}
}
