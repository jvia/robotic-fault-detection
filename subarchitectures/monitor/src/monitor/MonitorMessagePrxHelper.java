// **********************************************************************
//
// Copyright (c) 2003-2009 ZeroC, Inc. All rights reserved.
//
// This copy of Ice is licensed to you under the terms described in the
// ICE_LICENSE file included in this distribution.
//
// **********************************************************************

// Ice version 3.3.1

package monitor;

public final class MonitorMessagePrxHelper extends Ice.ObjectPrxHelperBase implements MonitorMessagePrx
{
    public static MonitorMessagePrx
    checkedCast(Ice.ObjectPrx __obj)
    {
        MonitorMessagePrx __d = null;
        if(__obj != null)
        {
            try
            {
                __d = (MonitorMessagePrx)__obj;
            }
            catch(ClassCastException ex)
            {
                if(__obj.ice_isA("::monitor::MonitorMessage"))
                {
                    MonitorMessagePrxHelper __h = new MonitorMessagePrxHelper();
                    __h.__copyFrom(__obj);
                    __d = __h;
                }
            }
        }
        return __d;
    }

    public static MonitorMessagePrx
    checkedCast(Ice.ObjectPrx __obj, java.util.Map<String, String> __ctx)
    {
        MonitorMessagePrx __d = null;
        if(__obj != null)
        {
            try
            {
                __d = (MonitorMessagePrx)__obj;
            }
            catch(ClassCastException ex)
            {
                if(__obj.ice_isA("::monitor::MonitorMessage", __ctx))
                {
                    MonitorMessagePrxHelper __h = new MonitorMessagePrxHelper();
                    __h.__copyFrom(__obj);
                    __d = __h;
                }
            }
        }
        return __d;
    }

    public static MonitorMessagePrx
    checkedCast(Ice.ObjectPrx __obj, String __facet)
    {
        MonitorMessagePrx __d = null;
        if(__obj != null)
        {
            Ice.ObjectPrx __bb = __obj.ice_facet(__facet);
            try
            {
                if(__bb.ice_isA("::monitor::MonitorMessage"))
                {
                    MonitorMessagePrxHelper __h = new MonitorMessagePrxHelper();
                    __h.__copyFrom(__bb);
                    __d = __h;
                }
            }
            catch(Ice.FacetNotExistException ex)
            {
            }
        }
        return __d;
    }

    public static MonitorMessagePrx
    checkedCast(Ice.ObjectPrx __obj, String __facet, java.util.Map<String, String> __ctx)
    {
        MonitorMessagePrx __d = null;
        if(__obj != null)
        {
            Ice.ObjectPrx __bb = __obj.ice_facet(__facet);
            try
            {
                if(__bb.ice_isA("::monitor::MonitorMessage", __ctx))
                {
                    MonitorMessagePrxHelper __h = new MonitorMessagePrxHelper();
                    __h.__copyFrom(__bb);
                    __d = __h;
                }
            }
            catch(Ice.FacetNotExistException ex)
            {
            }
        }
        return __d;
    }

    public static MonitorMessagePrx
    uncheckedCast(Ice.ObjectPrx __obj)
    {
        MonitorMessagePrx __d = null;
        if(__obj != null)
        {
            try
            {
                __d = (MonitorMessagePrx)__obj;
            }
            catch(ClassCastException ex)
            {
                MonitorMessagePrxHelper __h = new MonitorMessagePrxHelper();
                __h.__copyFrom(__obj);
                __d = __h;
            }
        }
        return __d;
    }

    public static MonitorMessagePrx
    uncheckedCast(Ice.ObjectPrx __obj, String __facet)
    {
        MonitorMessagePrx __d = null;
        if(__obj != null)
        {
            Ice.ObjectPrx __bb = __obj.ice_facet(__facet);
            MonitorMessagePrxHelper __h = new MonitorMessagePrxHelper();
            __h.__copyFrom(__bb);
            __d = __h;
        }
        return __d;
    }

    protected Ice._ObjectDelM
    __createDelegateM()
    {
        return new _MonitorMessageDelM();
    }

    protected Ice._ObjectDelD
    __createDelegateD()
    {
        return new _MonitorMessageDelD();
    }

    public static void
    __write(IceInternal.BasicStream __os, MonitorMessagePrx v)
    {
        __os.writeProxy(v);
    }

    public static MonitorMessagePrx
    __read(IceInternal.BasicStream __is)
    {
        Ice.ObjectPrx proxy = __is.readProxy();
        if(proxy != null)
        {
            MonitorMessagePrxHelper result = new MonitorMessagePrxHelper();
            result.__copyFrom(proxy);
            return result;
        }
        return null;
    }
}
