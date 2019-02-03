package pro.litvinovg.libreoffice.comp;

import com.sun.star.uno.XComponentContext;
import com.sun.star.lib.uno.helper.Factory;

import pro.litvinovg.libreoffice.dialog.ActionOneDialog;
import pro.litvinovg.libreoffice.helper.DialogHelper;

import com.sun.star.lang.XSingleComponentFactory;
import com.sun.star.registry.XRegistryKey;
import com.sun.star.lib.uno.helper.WeakBase;


public final class HacksProjectImpl extends WeakBase
   implements com.sun.star.lang.XServiceInfo,
              com.sun.star.task.XJobExecutor
{
    private final XComponentContext m_xContext;
    private static final String m_implementationName = HacksProjectImpl.class.getName();
    private static final String[] m_serviceNames = {
        "pro.litvinovg.libreoffice.HacksProject" };


    public HacksProjectImpl( XComponentContext context )
    {
        m_xContext = context;
    };

    public static XSingleComponentFactory __getComponentFactory( String sImplementationName ) {
        XSingleComponentFactory xFactory = null;

        if ( sImplementationName.equals( m_implementationName ) )
            xFactory = Factory.createComponentFactory(HacksProjectImpl.class, m_serviceNames);
        return xFactory;
    }

    public static boolean __writeRegistryServiceInfo( XRegistryKey xRegistryKey ) {
        return Factory.writeRegistryServiceInfo(m_implementationName,
                                                m_serviceNames,
                                                xRegistryKey);
    }

    // com.sun.star.lang.XServiceInfo:
    public String getImplementationName() {
         return m_implementationName;
    }

    public boolean supportsService( String sService ) {
        int len = m_serviceNames.length;

        for( int i=0; i < len; i++) {
            if (sService.equals(m_serviceNames[i]))
                return true;
        }
        return false;
    }

    public String[] getSupportedServiceNames() {
        return m_serviceNames;
    }

    // com.sun.star.task.XJobExecutor:
    public void trigger(String action)
    {
    	switch (action) {
    	case "actionOne":
    		ActionOneDialog actionOneDialog = new ActionOneDialog(m_xContext);
    		actionOneDialog.show();
    		break;
    	default:
    		DialogHelper.showErrorMessage(m_xContext, null, "Unknown action: " + action);
    	}
        
    }

}
