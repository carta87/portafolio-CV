import { Component, OnInit, AfterViewInit, HostListener } from '@angular/core';
import * as AOS from 'aos';
import gsap from 'gsap';

@Component({
  selector: 'app-portafolio',
  templateUrl: './portafolio.component.html',
  styleUrls: ['./portafolio.component.scss']
})
export class PortafolioComponent implements OnInit, AfterViewInit {

  private menuOpen = false;

  ngOnInit(): void {
    AOS.init({
      duration: 500,
      once: true
    });
  }

  ngAfterViewInit(): void {
    const tl = gsap.timeline({ defaults: { ease: 'power1.out' } });

    tl.from('.navBar', { y: '-10rem', duration: 1 });
    tl.from('.navBar__menu ul li', {
      y: '-2rem',
      opacity: 0,
      duration: 1,
      stagger: 0.25,
    });

    tl.to(
      '.sec1__contentDetail h1',
      { y: '0rem', duration: 1, stagger: 0.25 },
      '-=1'
    );

    tl.from('.sec1__imgBx img', { opacity: 0, x: '50%', duration: 1.5 });
    tl.from('.sec1__design3', { opacity: 0, x: '-30%', duration: 1.5 }, '-=1');
    tl.from('.sec1__content a', { opacity: 0, duration: 1.5 }, '-=1');
  }

  /** Menú mobile */
  toggleMenu(): void {
    this.menuOpen = !this.menuOpen;

    const menuIcon = document.querySelector('.sec1__menuIcon');
    const menuBar = document.querySelector('.menuBar');

    menuIcon?.classList.toggle('active', this.menuOpen);
    menuBar?.classList.toggle('active', this.menuOpen);
  }

  /** Scroll top */
  scrollToTop(): void {
    window.scrollTo({ top: 0, behavior: 'smooth' });
  }

  /** Mostrar botón scroll */
  @HostListener('window:scroll', [])
  onScroll(): void {
    const btn = document.querySelector('.scroll-top');
    btn?.classList.toggle('active', window.scrollY > 500);
  }

  proyectos = [
    { nombre: 'Hoja de vida', url: 'https://cv-carlos-tafur.netlify.app/' },
    { nombre: 'Proyecto Vengadores', url: 'https://mt-htmlsena.netlify.app/home.html' },
    { nombre: 'AlphaPets', url: 'https://alphapets-bictia.netlify.app/' },
    { nombre: 'Calculadora', url: 'https://calculadora-angularmt.netlify.app/' },
    { nombre: 'Portal de peliculas', url: 'https://peliculasmetnet.netlify.app/' },
    { nombre: 'Crud Angular', url: 'https://crud-angular-mtafur.netlify.app/' }
  ];
}
