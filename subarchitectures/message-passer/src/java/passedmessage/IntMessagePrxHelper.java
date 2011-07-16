// **********************************************************************
//
// Copyright (c) 2003-2009 ZeroC, Inc. All rights reserved.
//
// This copy of Ice is licensed to you under the terms described in the
// ICE_LICENSE file included in this distribution.
//
// **********************************************************************

// Ice version 3.3.1

package passedmessage;

public final class IntMessagePrxHelper extends Ice.ObjectPrxHelperBase implements IntMessagePrx
{
    public static IntMessagePrx
    checkedCast(Ice.ObjectPrx __obj)
    {
        IntMessagePrx __d = null;
        if(__obj != null)
        {
            try
            {
                __d = (IntMessagePrx)__obj;
            }
            catch(ClassCastException ex)
            {
                if(__obj.ice_isA("::passedmessage::IntMessage"))
                {
                    IntMessagePrxHelper __h = new IntMessagePrxHelper();
                    __h.__copyFrom(__obj);
                    __d = __h;
                }
            }
        }
        return __d;
    }

    public static IntMessagePrx
    checkedCast(Ice.ObjectPrx __obj, java.util.Map<String, String> __ctx)
    {
        IntMessagePrx __d = null;
        if(__obj != null)
        {
            try
            {
                __d = (IntMessagePrx)__obj;
            }
            catch(ClassCastException ex)
            {
                if(__obj.ice_isA("::passedmessage::IntMessage", __ctx))
                {
                    IntMessagePrxHelper __h = new IntMessagePrxHelper();
                    __h.__copyFrom(__obj);
                    __d = __h;
                }
            }
        }
        return __d;
    }

    public static IntMessagePrx
    checkedCast(Ice.ObjectPrx __obj, String __facet)
    {
        IntMessagePrx __d = null;
        if(__obj != null)
        {
            Ice.ObjectPrx __bb = __obj.ice_facet(__facet);
            try
            {
                if(__bb.ice_isA("::passedmessage::IntMessage"))
                {
                    IntMessagePrxHelper __h = new IntMessagePrxHelper();
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

    public static IntMessagePrx
    checkedCast(Ice.ObjectPrx __obj, String __facet, java.util.Map<String, String> __ctx)
    {
        IntMessagePrx __d = null;
        if(__obj != null)
        {
            Ice.ObjectPrx __bb = __obj.ice_facet(__facet);
            try
            {
                if(__bb.ice_isA("::passedmessage::IntMessage", __ctx))
                {
                    IntMessagePrxHelper __h = new IntMessagePrxHelper();
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

    public static IntMessagePrx
    uncheckedCast(Ice.ObjectPrx __obj)
    {
        IntMessagePrx __d = null;
        if(__obj != null)
        {
            try
            {
                __d = (IntMessagePrx)__obj;
            }
            catch(ClassCastException ex)
            {
                IntMessagePrxHelper __h = new IntMessagePrxHelper();
                __h.__copyFrom(__obj);
                __d = __h;
            }
        }
        return __d;
    }

    public static IntMessagePrx
    uncheckedCast(Ice.ObjectPrx __obj, String __facet)
    {
        IntMessagePrx __d = null;
        if(__obj != null)
        {
            Ice.ObjectPrx __bb = __obj.ice_facet(__facet);
            IntMessagePrxHelper __h = new IntMessagePrxHelper();
            __h.__copyFrom(__bb);
            __d = __h;
        }
        return __d;
    }

    protected Ice._ObjectDelM
    __createDelegateM()
    {
        return new _IntMessageDelM();
    }

    protected Ice._ObjectDelD
    __createDelegateD()
    {
        return new _IntMessageDelD();
    }

    public static void
    __write(IceInternal.BasicStream __os, IntMessagePrx v)
    {
        __os.writeProxy(v);
    }

    public static IntMessagePrx
    __read(IceInternal.BasicStream __is)
    {
        Ice.ObjectPrx proxy = __is.readProxy();
        if(proxy != null)
        {
            IntMessagePrxHelper result = new IntMessagePrxHelper();
            result.__copyFrom(proxy);
            return result;
        }
        return null;
    }
}
