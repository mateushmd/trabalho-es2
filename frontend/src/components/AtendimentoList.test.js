import React from 'react';
import { render, screen } from '@testing-library/react';
import '@testing-library/jest-dom';
import AtendimentoList from './AtendimentoList';

test('renders Atendimentos header', () => {
  render(<AtendimentoList atendimentos={[]} profissionais={[]} onUpdate={() => {}} />);
  const headerElement = screen.getByText(/Atendimentos/i);
  expect(headerElement).toBeInTheDocument();
});
