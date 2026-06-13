import React from 'react';
import { render, screen } from '@testing-library/react';
import '@testing-library/jest-dom';
import ExameLabList from './ExameLabList';

test('renders Exames de Laboratório header', () => {
  render(<ExameLabList exames={[]} atendimentos={[]} onUpdate={() => {}} />);
  const headerElement = screen.getByText(/Exames de Laboratório/i);
  expect(headerElement).toBeInTheDocument();
});
