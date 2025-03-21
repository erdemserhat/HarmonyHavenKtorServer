// Initialize AOS
AOS.init({
    duration: 800,
    once: false,
    mirror: true
});

// Initialize particles.js
particlesJS('particles-js', {
    particles: {
        number: {
            value: 80,
            density: {
                enable: true,
                value_area: 800
            }
        },
        color: {
            value: '#ffffff'
        },
        shape: {
            type: 'circle'
        },
        opacity: {
            value: 0.5,
            random: false,
            animation: {
                enable: true,
                speed: 1,
                opacity_min: 0.1,
                sync: false
            }
        },
        size: {
            value: 3,
            random: true,
            animation: {
                enable: true,
                speed: 2,
                size_min: 0.1,
                sync: false
            }
        },
        line_linked: {
            enable: true,
            distance: 150,
            color: '#ffffff',
            opacity: 0.4,
            width: 1
        },
        move: {
            enable: true,
            speed: 2,
            direction: 'none',
            random: false,
            straight: false,
            out_mode: 'out',
            bounce: false,
        }
    },
    interactivity: {
        detect_on: 'canvas',
        events: {
            onhover: {
                enable: true,
                mode: 'grab'
            },
            onclick: {
                enable: true,
                mode: 'push'
            },
            resize: true
        },
        modes: {
            grab: {
                distance: 140,
                line_linked: {
                    opacity: 1
                }
            },
            push: {
                particles_nb: 4
            }
        }
    },
    retina_detect: true
});

// Get all elements with language attributes
const elements = document.querySelectorAll('[data-en]');
const enButton = document.getElementById('en-btn');
const trButton = document.getElementById('tr-btn');

// Set default language
let currentLanguage = 'en';

// Function to change language
function changeLanguage(lang) {
    currentLanguage = lang;
    
    // Update button states
    enButton.classList.toggle('active', lang === 'en');
    trButton.classList.toggle('active', lang === 'tr');
    
    // Update text content for all elements
    elements.forEach(element => {
        element.textContent = element.getAttribute(`data-${lang}`);
    });
    
    // Save language preference
    localStorage.setItem('preferred-language', lang);
}

// Load preferred language from localStorage
document.addEventListener('DOMContentLoaded', () => {
    const savedLanguage = localStorage.getItem('preferred-language');
    if (savedLanguage) {
        changeLanguage(savedLanguage);
    } else {
        // Default to Turkish if user's browser language is Turkish
        const browserLang = navigator.language || navigator.userLanguage;
        if (browserLang.startsWith('tr')) {
            changeLanguage('tr');
        } else {
            changeLanguage('en');
        }
    }
    
    // Add smooth scroll behavior for anchor links
    document.querySelectorAll('a[href^="#"]').forEach(anchor => {
        anchor.addEventListener('click', function (e) {
            e.preventDefault();
            document.querySelector(this.getAttribute('href')).scrollIntoView({
                behavior: 'smooth'
            });
        });
    });
    
    // Refresh AOS on language change to ensure animations work properly
    document.querySelectorAll('.lang-btn').forEach(btn => {
        btn.addEventListener('click', () => {
            setTimeout(() => {
                AOS.refresh();
            }, 100);
        });
    });
}); 