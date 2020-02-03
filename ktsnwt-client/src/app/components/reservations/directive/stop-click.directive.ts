import { Directive, HostListener, Output, Renderer2, ElementRef, OnDestroy, OnInit, EventEmitter } from '@angular/core';

@Directive(
  { selector: '[appModifiedClick]' }
)
export class StopClickDirective implements OnInit, OnDestroy {
  @Output('appModifiedClick') clicked = new EventEmitter<Event>();

  private unlisten: Function;

  constructor(private renderer: Renderer2, private el: ElementRef) { }

  ngOnInit() {
    this.unlisten = this.renderer.listen(
      this.el.nativeElement,
      'click',
      (event: Event) => {
        event.stopPropagation();
        this.clicked.emit(event);
        console.log('dawd');
      }
    );
  }

  ngOnDestroy() {
    if (this.unlisten) {
      this.unlisten();
    }
  }
}
