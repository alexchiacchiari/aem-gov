(($) => {
    const $els = $(".cmp-menu a, .cmp-menu header");
    const count = $els.length;
    const groupLength = Math.ceil(count/3);
    let groupNumber = 0;
    let i = 1;
    $('.cmp-menu').css('--count', count + '');
    $els.each(() => {
        if (i > groupLength) {
            groupNumber++;
            i = 1;
        }
        $(this).attr('data-group', groupNumber);
        i++;
    });
    $('.cmp-menu footer button').on('click',(e) => {
        e.preventDefault();
        $els.each((j)=>{
            $(this).css('--top',$(this)[0].getBoundingClientRect().top + ($(this).attr('data-group') * -15) - 20);
            $(this).css('--delay-in',j*.1+'s');
            $(this).css('--delay-out',(count-j)*.1+'s');
        });
        $('.cmp-menu').toggleClass('closed');
        e.stopPropagation();
    });

// run animation once at beginning for demo
    setTimeout(function(){
        $('.cmp-menu footer button').click();
        setTimeout(function(){
            $('.cmp-menu footer button').click();
        }, (count * 100) + 500 );
    }, 1000);

})(jQuery);
