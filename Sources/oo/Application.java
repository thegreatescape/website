package oo;

import er.extensions.appserver.ERXApplication;

public class Application extends ERXApplication {

	public static void main( String[] argv ) {
		ERXApplication.main( argv, Application.class );
	}

	public Application() {
		setDefaultRequestHandler( requestHandlerForKey( directActionRequestHandlerKey() ) );
	}
}