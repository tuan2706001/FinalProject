new Splide(".splide", {
    type: "loop",
    perPage: 5,
    autoplay: true,
    gap: 50,
    arrows: false,
    // wheel: true,
    speed: 1000,
    breakpoints: {
        1024: {
            perPage: 4,
            gap    : '.7rem',
        },
        768: {
            perPage: 3,
            gap    : '.7rem',
        },
        640: {
            perPage: 2,
            gap    : '.7rem',
        },
        480: {
            perPage: 1,
            gap    : '.7rem',
        },
    },
}).mount();