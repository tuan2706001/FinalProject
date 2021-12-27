new Splide( '.splide1', {
    rewind: true,
    perPage  : 6,
    pagination: false,
    width: '90%',
    gap:100,
    autoplay: true,
    arrows: true,
    breakpoints: {
        640: {
            perPage : 2,
        },
        1024:{
            perPage : 3,
        }
    },
} ).mount();