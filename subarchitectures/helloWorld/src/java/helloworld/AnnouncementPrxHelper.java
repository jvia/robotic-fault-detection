// **********************************************************************
//
// Copyright (c) 2003-2011 ZeroC, Inc. All rights reserved.
//
// This copy of Ice is licensed to you under the terms described in the
// ICE_LICENSE file included in this distribution.
//
// **********************************************************************
//
// Ice version 3.4.2
//
// <auto-generated>
//
// Generated from file `AnnouncementPrxHelper.java'
//
// Warning: do not edit this file.
//
// </auto-generated>
//

package helloWorld.src.java.helloworld;

public final class AnnouncementPrxHelper extends Ice.ObjectPrxHelperBase implements AnnouncementPrx
{
    public static AnnouncementPrx
    checkedCast(Ice.ObjectPrx __obj)
    {
        AnnouncementPrx __d = null;
        if(__obj != null)
        {
            try
            {
                __d = (AnnouncementPrx)__obj;
            }
            catch(ClassCastException ex)
            {
                if(__obj.ice_isA(ice_staticId()))
                {
                    AnnouncementPrxHelper __h = new AnnouncementPrxHelper();
                    __h.__copyFrom(__obj);
                    __d = __h;
                }
            }
        }
        return __d;
    }

    public static AnnouncementPrx
    checkedCast(Ice.ObjectPrx __obj, java.util.Map<String, String> __ctx)
    {
        AnnouncementPrx __d = null;
        if(__obj != null)
        {
            try
            {
                __d = (AnnouncementPrx)__obj;
            }
            catch(ClassCastException ex)
            {
                if(__obj.ice_isA(ice_staticId(), __ctx))
                {
                    AnnouncementPrxHelper __h = new AnnouncementPrxHelper();
                    __h.__copyFrom(__obj);
                    __d = __h;
                }
            }
        }
        return __d;
    }

    public static AnnouncementPrx
    checkedCast(Ice.ObjectPrx __obj, String __facet)
    {
        AnnouncementPrx __d = null;
        if(__obj != null)
        {
            Ice.ObjectPrx __bb = __obj.ice_facet(__facet);
            try
            {
                if(__bb.ice_isA(ice_staticId()))
                {
                    AnnouncementPrxHelper __h = new AnnouncementPrxHelper();
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

    public static AnnouncementPrx
    checkedCast(Ice.ObjectPrx __obj, String __facet, java.util.Map<String, String> __ctx)
    {
        AnnouncementPrx __d = null;
        if(__obj != null)
        {
            Ice.ObjectPrx __bb = __obj.ice_facet(__facet);
            try
            {
                if(__bb.ice_isA(ice_staticId(), __ctx))
                {
                    AnnouncementPrxHelper __h = new AnnouncementPrxHelper();
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

    public static AnnouncementPrx
    uncheckedCast(Ice.ObjectPrx __obj)
    {
        AnnouncementPrx __d = null;
        if(__obj != null)
        {
            try
            {
                __d = (AnnouncementPrx)__obj;
            }
            catch(ClassCastException ex)
            {
                AnnouncementPrxHelper __h = new AnnouncementPrxHelper();
                __h.__copyFrom(__obj);
                __d = __h;
            }
        }
        return __d;
    }

    public static AnnouncementPrx
    uncheckedCast(Ice.ObjectPrx __obj, String __facet)
    {
        AnnouncementPrx __d = null;
        if(__obj != null)
        {
            Ice.ObjectPrx __bb = __obj.ice_facet(__facet);
            AnnouncementPrxHelper __h = new AnnouncementPrxHelper();
            __h.__copyFrom(__bb);
            __d = __h;
        }
        return __d;
    }

    public static final String[] __ids =
    {
        "::Ice::Object",
        "::helloworld::Announcement"
    };

    public static String
    ice_staticId()
    {
        return __ids[1];
    }

    protected Ice._ObjectDelM
    __createDelegateM()
    {
        return new _AnnouncementDelM();
    }

    protected Ice._ObjectDelD
    __createDelegateD()
    {
        return new _AnnouncementDelD();
    }

    public static void
    __write(IceInternal.BasicStream __os, AnnouncementPrx v)
    {
        __os.writeProxy(v);
    }

    public static AnnouncementPrx
    __read(IceInternal.BasicStream __is)
    {
        Ice.ObjectPrx proxy = __is.readProxy();
        if(proxy != null)
        {
            AnnouncementPrxHelper result = new AnnouncementPrxHelper();
            result.__copyFrom(proxy);
            return result;
        }
        return null;
    }
}
