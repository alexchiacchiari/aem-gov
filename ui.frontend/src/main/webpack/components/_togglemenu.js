((document) => {
  document.querySelector("#menuButton").addEventListener("click", () => {
      console.log(document.querySelector("#menuButton").children);
  });
})(document);
