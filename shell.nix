{ pkgs ? import <nixpkgs> {} }:

pkgs.mkShell {
  buildInputs = [
    pkgs.jdk17
    pkgs.maven
    pkgs.nodejs_20
    pkgs.docker-compose
  ];
}
