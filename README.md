Easy-PullToRefresh-Android
==========================

**OverScrollListView** is a drop-in replacement for `ListView`.

The **OverScrollListView** class implements the bounce effect & pull-to-refresh feature for ListView(this implementation can also be applied to `ExpandableListView`).

This pull-to-refresh implementation is inspired by [XListView](https://github.com/Maxwin-z/XListView-Android) with the idea of adjusting height of the header view while pulling the `ListView`.

For the bounce effect, it simply intercepts touch events and detects if the scrolling has reached the top or bottom edge, if so, we call scrollTo() to scroll the entire the `ListView` off the screen, and then with a `Scroller`, we compute the Y scroll positions and create a smooth bounce effect.

For pull-to-refresh, it uses a header view which implements the `PullToRefreshCallback` interface as the indicator view for displaying "Pull to refresh", "Release to refresh", "Loading..." and an arrow image. Of course, you can implement `PullToRefreshCallback` and write your own `PullToRefreshHeaderView`, as long as you follow some requirements for the layout of the header view, take the default `PullToRefreshHeaderView` as a referenece.

**NOTE**: If you do not what the pull-to-refresh feature, you can still use **OverScrollListView**, in that case, **OverScrollListView** only offers you the bounce effect, and that is why it has the name. just remember not to call `setPullToRefreshHeaderView()`.

Under MIT license
=================

    Copyright (c) 2013 neevek <i at neevek.net>

    See the file license.txt for copying permission.
