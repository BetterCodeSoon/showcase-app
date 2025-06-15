package com.showcase.backend.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

/***
 * This class solely exist to demonstrate using UUID v7 for generation of entity id's
 * <br>
 *  * <p>
 *  * To see how ensuring UUIDv7 is achieved on the database layer at initialization see: <br>
 *  * /ProjectRoot<a href="../../../../../scripts/initdb/02-ensure-uuid-v7.sql">/scripts/initdb/02-ensure-uuid-v7.sql</a>
 *  </p>
 *  * <p>
 *  *  For more info on features of this version of unique identifier (like time-sortability), see: <a href="https://uuid7.com/">https://uuid7.com/</a>
 *  </p>
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@RequiredArgsConstructor
@Getter
@Setter
@Builder
public class Note implements IdAble<UUID>, Serializable {

  /// For UUIDv7 generation on the database layer see
  /// /ProjectRoot[/scripts/initdb/02-ensure-uuid-v7.sql](../../../../../scripts/initdb/02-ensure-uuid-v7.sql)
  @Id
  @Column(columnDefinition = "UUID")
  private UUID id;

  @NotNull
  @NonNull
  private String category;

  @NotNull
  @NonNull
  private String content;
}
