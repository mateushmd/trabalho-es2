import React from 'react';
import { render, screen } from '@testing-library/react';
import '@testing-library/jest-dom';
import ProfissionalList from './ProfissionalList';

test('renders Profissionais de Saúde header', () => {
  render(<ProfissionalList profissionais={[]} onUpdate={() => {}} />);
  const headerElement = screen.getByText(/Profissionais de Saúde/i);
  expect(headerElement).toBeInTheDocument();
});
