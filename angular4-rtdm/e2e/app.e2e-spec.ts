import { Angular4RtdmPage } from './app.po';

describe('angular4-rtdm App', () => {
  let page: Angular4RtdmPage;

  beforeEach(() => {
    page = new Angular4RtdmPage();
  });

  it('should display welcome message', () => {
    page.navigateTo();
    expect(page.getParagraphText()).toEqual('Welcome to app!!');
  });
});
