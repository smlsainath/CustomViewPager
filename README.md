# CustomViewPager

Auto Scrolling View Pager with touch events handled.

Also can be customized for setting the timer delay.

Add below code in the attrs.xml file for setting the 'autoScroll' and 'enableOnlyOneRoundOfScrolling' in the xml itself 
    
    <declare-styleable name="ImagesViewPagerAttrs">
        <attr name="auto_scroll" format="boolean" /> //default true
        <attr name="enable_only_one_round_of_scrolling" format="boolean"/> // default false
        <attr name="disable_finger_scroll" format="boolean" /> //default false
    </declare-styleable>
    
Check the CustomViewPagerSample for more info
