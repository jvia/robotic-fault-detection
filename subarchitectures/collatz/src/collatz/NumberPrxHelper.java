// **********************************************************************
//
// Copyright (c) 2003-2009 ZeroC, Inc. All rights reserved.
//
// This copy of Ice is licensed to you under the terms described in the
// ICE_LICENSE file included in this distribution.
//
// **********************************************************************

// Ice version 3.3.1

package collatz;

public final class NumberPrxHelper extends Ice.ObjectPrxHelperBase implements NumberPrx
{
    public static NumberPrx
    checkedCast(Ice.ObjectPrx __obj)
    {
        NumberPrx __d = null;
        if(__obj != null)
        {
            try
            {
                __d = (NumberPrx)__obj;
            }
            catch(ClassCastException ex)
            {
                if(__obj.ice_isA("::collatz::Number"))
                {
                    NumberPrxHelper __h = new NumberPrxHelper();
                    __h.__copyFrom(__obj);
                    __d = __h;
                }
            }
        }
        return __d;
    }

    public static NumberPrx
    checkedCast(Ice.ObjectPrx __obj, java.util.Map<String, String> __ctx)
    {
        NumberPrx __d = null;
        if(__obj != null)
        {
            try
            {
                __d = (NumberPrx)__obj;
            }
            catch(ClassCastException ex)
            {
                if(__obj.ice_isA("::collatz::Number", __ctx))
                {
                    NumberPrxHelper __h = new NumberPrxHelper();
                    __h.__copyFrom(__obj);
                    __d = __h;
                }
            }
        }
        return __d;
    }

    public static NumberPrx
    checkedCast(Ice.ObjectPrx __obj, String __facet)
    {
        NumberPrx __d = null;
        if(__obj != null)
        {
            Ice.ObjectPrx __bb = __obj.ice_facet(__facet);
            try
            {
                if(__bb.ice_isA("::collatz::Number"))
                {
                    NumberPrxHelper __h = new NumberPrxHelper();
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

    public static NumberPrx
    checkedCast(Ice.ObjectPrx __obj, String __facet, java.util.Map<String, String> __ctx)
    {
        NumberPrx __d = null;
        if(__obj != null)
        {
            Ice.ObjectPrx __bb = __obj.ice_facet(__facet);
            try
            {
                if(__bb.ice_isA("::collatz::Number", __ctx))
                {
                    NumberPrxHelper __h = new NumberPrxHelper();
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

    public static NumberPrx
    uncheckedCast(Ice.ObjectPrx __obj)
    {
        NumberPrx __d = null;
        if(__obj != null)
        {
            try
            {
                __d = (NumberPrx)__obj;
            }
            catch(ClassCastException ex)
            {
                NumberPrxHelper __h = new NumberPrxHelper();
                __h.__copyFrom(__obj);
                __d = __h;
            }
        }
        return __d;
    }

    public static NumberPrx
    uncheckedCast(Ice.ObjectPrx __obj, String __facet)
    {
        NumberPrx __d = null;
        if(__obj != null)
        {
            Ice.ObjectPrx __bb = __obj.ice_facet(__facet);
            NumberPrxHelper __h = new NumberPrxHelper();
            __h.__copyFrom(__bb);
            __d = __h;
        }
        return __d;
    }

    protected Ice._ObjectDelM
    __createDelegateM()
    {
        return new _NumberDelM();
    }

    protected Ice._ObjectDelD
    __createDelegateD()
    {
        return new _NumberDelD();
    }

    public static void
    __write(IceInternal.BasicStream __os, NumberPrx v)
    {
        __os.writeProxy(v);
    }

    public static NumberPrx
    __read(IceInternal.BasicStream __is)
    {
        Ice.ObjectPrx proxy = __is.readProxy();
        if(proxy != null)
        {
            NumberPrxHelper result = new NumberPrxHelper();
            result.__copyFrom(proxy);
            return result;
        }
        return null;
    }
}
