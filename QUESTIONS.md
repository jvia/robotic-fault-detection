    Questions
==========

### CAST ###

- Where should I be building my subarchitecture?

  I should be building it in my own repository, outside of CAST.

- Is the skeleton system okay or should I make an even smaller system?

  Make an even smaller system. The skeleton is really the opposite of
  what the name implies and is, in fact, a system containing everything.
  So, I should make my own system with fake subarchitectures that
  model the various ways which data pass through working memory.

- What are the patterns that I need to model?

  message passing (write then delete)
  message passing w/ return (write then overwrite then delete by original)
  state update (continual overwrite)
  casual chains
  causal chains in addition (just adds)
  
