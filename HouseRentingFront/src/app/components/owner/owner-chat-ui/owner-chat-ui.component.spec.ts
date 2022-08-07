import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OwnerChatUiComponent } from './owner-chat-ui.component';

describe('OwnerChatUiComponent', () => {
  let component: OwnerChatUiComponent;
  let fixture: ComponentFixture<OwnerChatUiComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ OwnerChatUiComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(OwnerChatUiComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
