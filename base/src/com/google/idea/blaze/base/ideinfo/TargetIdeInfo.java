/*
 * Copyright 2016 The Bazel Authors. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.google.idea.blaze.base.ideinfo;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.idea.blaze.base.dependencies.TargetInfo;
import com.google.idea.blaze.base.ideinfo.Dependency.DependencyType;
import com.google.idea.blaze.base.model.primitives.Kind;
import com.google.idea.blaze.base.model.primitives.Label;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.Nullable;

/** Simple implementation of TargetIdeInfo. */
public final class TargetIdeInfo implements Serializable {
  private static final long serialVersionUID = 19L;

  private final TargetKey key;
  private final Kind kind;
  @Nullable private final ArtifactLocation buildFile;
  private final Collection<Dependency> dependencies;
  private final Collection<String> tags;
  private final List<ArtifactLocation> sources;
  @Nullable private final CIdeInfo cIdeInfo;
  @Nullable private final CToolchainIdeInfo cToolchainIdeInfo;
  @Nullable private final JavaIdeInfo javaIdeInfo;
  @Nullable private final AndroidIdeInfo androidIdeInfo;
  @Nullable private final AndroidSdkIdeInfo androidSdkIdeInfo;
  @Nullable private final AndroidAarIdeInfo androidAarIdeInfo;
  @Nullable private final PyIdeInfo pyIdeInfo;
  @Nullable private final GoIdeInfo goIdeInfo;
  @Nullable private final JsIdeInfo jsIdeInfo;
  @Nullable private final TsIdeInfo tsIdeInfo;
  @Nullable private final DartIdeInfo dartIdeInfo;
  @Nullable private final TestIdeInfo testIdeInfo;
  @Nullable private final JavaToolchainIdeInfo javaToolchainIdeInfo;
  @Nullable private final KotlinToolchainIdeInfo kotlinToolchainIdeInfo;

  public TargetIdeInfo(
      TargetKey key,
      Kind kind,
      @Nullable ArtifactLocation buildFile,
      Collection<Dependency> dependencies,
      Collection<String> tags,
      Set<ArtifactLocation> sources,
      @Nullable CIdeInfo cIdeInfo,
      @Nullable CToolchainIdeInfo cToolchainIdeInfo,
      @Nullable JavaIdeInfo javaIdeInfo,
      @Nullable AndroidIdeInfo androidIdeInfo,
      @Nullable AndroidSdkIdeInfo androidSdkIdeInfo,
      @Nullable AndroidAarIdeInfo androidAarIdeInfo,
      @Nullable PyIdeInfo pyIdeInfo,
      @Nullable GoIdeInfo goIdeInfo,
      @Nullable JsIdeInfo jsIdeInfo,
      @Nullable TsIdeInfo tsIdeInfo,
      @Nullable DartIdeInfo dartIdeInfo,
      @Nullable TestIdeInfo testIdeInfo,
      @Nullable JavaToolchainIdeInfo javaToolchainIdeInfo,
      @Nullable KotlinToolchainIdeInfo kotlinToolchainIdeInfo) {
    this.key = key;
    this.kind = kind;
    this.buildFile = buildFile;
    this.dependencies = dependencies;
    this.tags = tags;
    // can't convert to a set without bumping the serialization version
    this.sources = new ArrayList<>(sources);
    this.cIdeInfo = cIdeInfo;
    this.cToolchainIdeInfo = cToolchainIdeInfo;
    this.javaIdeInfo = javaIdeInfo;
    this.androidIdeInfo = androidIdeInfo;
    this.androidSdkIdeInfo = androidSdkIdeInfo;
    this.androidAarIdeInfo = androidAarIdeInfo;
    this.pyIdeInfo = pyIdeInfo;
    this.goIdeInfo = goIdeInfo;
    this.jsIdeInfo = jsIdeInfo;
    this.tsIdeInfo = tsIdeInfo;
    this.dartIdeInfo = dartIdeInfo;
    this.testIdeInfo = testIdeInfo;
    this.javaToolchainIdeInfo = javaToolchainIdeInfo;
    this.kotlinToolchainIdeInfo = kotlinToolchainIdeInfo;
  }

  public TargetKey getKey() {
    return key;
  }

  public Kind getKind() {
    return kind;
  }

  @Nullable
  public ArtifactLocation getBuildFile() {
    return buildFile;
  }

  public Collection<Dependency> getDependencies() {
    return dependencies;
  }

  public Collection<String> getTags() {
    return tags;
  }

  public List<ArtifactLocation> getSources() {
    return sources;
  }

  @Nullable
  public CIdeInfo getcIdeInfo() {
    return cIdeInfo;
  }

  @Nullable
  public CToolchainIdeInfo getcToolchainIdeInfo() {
    return cToolchainIdeInfo;
  }

  @Nullable
  public JavaIdeInfo getJavaIdeInfo() {
    return javaIdeInfo;
  }

  @Nullable
  public AndroidIdeInfo getAndroidIdeInfo() {
    return androidIdeInfo;
  }

  @Nullable
  public AndroidSdkIdeInfo getAndroidSdkIdeInfo() {
    return androidSdkIdeInfo;
  }

  @Nullable
  public AndroidAarIdeInfo getAndroidAarIdeInfo() {
    return androidAarIdeInfo;
  }

  @Nullable
  public PyIdeInfo getPyIdeInfo() {
    return pyIdeInfo;
  }

  @Nullable
  public GoIdeInfo getGoIdeInfo() {
    return goIdeInfo;
  }

  @Nullable
  public JsIdeInfo getJsIdeInfo() {
    return jsIdeInfo;
  }

  @Nullable
  public TsIdeInfo getTsIdeInfo() {
    return tsIdeInfo;
  }

  @Nullable
  public DartIdeInfo getDartIdeInfo() {
    return dartIdeInfo;
  }

  @Nullable
  public TestIdeInfo getTestIdeInfo() {
    return testIdeInfo;
  }

  @Nullable
  public JavaToolchainIdeInfo getJavaToolchainIdeInfo() {
    return javaToolchainIdeInfo;
  }

  @Nullable
  public KotlinToolchainIdeInfo getKotlinToolchainIdeInfo() {
    return kotlinToolchainIdeInfo;
  }

  public TargetInfo toTargetInfo() {
    return TargetInfo.builder(getKey().getLabel(), getKind().toString())
        .setTestSize(getTestIdeInfo() != null ? getTestIdeInfo().getTestSize() : null)
        .setSources(ImmutableList.copyOf(getSources()))
        .build();
  }

  @Override
  public String toString() {
    return getKey().toString();
  }

  /** Returns whether this rule is one of the kinds. */
  public boolean kindIsOneOf(Kind... kinds) {
    return kindIsOneOf(Arrays.asList(kinds));
  }

  /** Returns whether this rule is one of the kinds. */
  public boolean kindIsOneOf(Collection<Kind> kinds) {
    if (getKind() != null) {
      return getKind().isOneOf(kinds);
    }
    return false;
  }

  public boolean isPlainTarget() {
    return getKey().isPlainTarget();
  }

  public static Builder builder() {
    return new Builder();
  }

  /** Builder for rule ide info */
  public static class Builder {
    private TargetKey key;
    private Kind kind;
    private ArtifactLocation buildFile;
    private final List<Dependency> dependencies = new ArrayList<>();
    private final List<String> tags = new ArrayList<>();
    private final Set<ArtifactLocation> sources = new HashSet<>();
    private CIdeInfo cIdeInfo;
    private CToolchainIdeInfo cToolchainIdeInfo;
    private JavaIdeInfo javaIdeInfo;
    private AndroidIdeInfo androidIdeInfo;
    private AndroidAarIdeInfo androidAarIdeInfo;
    private PyIdeInfo pyIdeInfo;
    private GoIdeInfo goIdeInfo;
    private JsIdeInfo jsIdeInfo;
    private TsIdeInfo tsIdeInfo;
    private DartIdeInfo dartIdeInfo;
    private TestIdeInfo testIdeInfo;
    private JavaToolchainIdeInfo javaToolchainIdeInfo;
    private KotlinToolchainIdeInfo kotlinToolchainIdeInfo;

    public Builder setLabel(String label) {
      return setLabel(Label.create(label));
    }

    public Builder setLabel(Label label) {
      this.key = TargetKey.forPlainTarget(label);
      return this;
    }

    public Builder setBuildFile(ArtifactLocation buildFile) {
      this.buildFile = buildFile;
      return this;
    }

    @VisibleForTesting
    public Builder setKind(String kindString) {
      Kind kind = Preconditions.checkNotNull(Kind.fromString(kindString));
      return setKind(kind);
    }

    public Builder setKind(Kind kind) {
      this.kind = kind;
      return this;
    }

    public Builder addSource(ArtifactLocation source) {
      this.sources.add(source);
      return this;
    }

    public Builder addSource(ArtifactLocation.Builder source) {
      return addSource(source.build());
    }

    public Builder setJavaInfo(JavaIdeInfo.Builder builder) {
      javaIdeInfo = builder.build();
      return this;
    }

    public Builder setCInfo(CIdeInfo.Builder cInfoBuilder) {
      this.cIdeInfo = cInfoBuilder.build();
      this.sources.addAll(cIdeInfo.getSources());
      this.sources.addAll(cIdeInfo.getHeaders());
      this.sources.addAll(cIdeInfo.getTextualHeaders());
      return this;
    }

    public Builder setCToolchainInfo(CToolchainIdeInfo.Builder info) {
      this.cToolchainIdeInfo = info.build();
      return this;
    }

    public Builder setAndroidInfo(AndroidIdeInfo.Builder androidInfo) {
      this.androidIdeInfo = androidInfo.build();
      return this;
    }

    public Builder setAndroidAarInfo(AndroidAarIdeInfo aarInfo) {
      this.androidAarIdeInfo = aarInfo;
      return this;
    }

    public Builder setPyInfo(PyIdeInfo.Builder pyInfo) {
      this.pyIdeInfo = pyInfo.build();
      return this;
    }

    public Builder setGoInfo(GoIdeInfo.Builder goInfo) {
      this.goIdeInfo = goInfo.build();
      return this;
    }

    public Builder setJsInfo(JsIdeInfo.Builder jsInfo) {
      this.jsIdeInfo = jsInfo.build();
      return this;
    }

    public Builder setTsInfo(TsIdeInfo.Builder tsInfo) {
      this.tsIdeInfo = tsInfo.build();
      return this;
    }

    public Builder setDartInfo(DartIdeInfo.Builder dartInfo) {
      this.dartIdeInfo = dartInfo.build();
      return this;
    }

    public Builder setTestInfo(TestIdeInfo.Builder testInfo) {
      this.testIdeInfo = testInfo.build();
      return this;
    }

    public Builder setJavaToolchainIdeInfo(JavaToolchainIdeInfo.Builder javaToolchainIdeInfo) {
      this.javaToolchainIdeInfo = javaToolchainIdeInfo.build();
      return this;
    }

    public Builder setKotlinToolchainIdeInfo(KotlinToolchainIdeInfo.Builder toolchain) {
      this.kotlinToolchainIdeInfo = toolchain.build();
      return this;
    }

    public Builder addTag(String s) {
      this.tags.add(s);
      return this;
    }

    public Builder addDependency(String s) {
      return addDependency(Label.create(s));
    }

    public Builder addDependency(Label label) {
      this.dependencies.add(
          new Dependency(TargetKey.forPlainTarget(label), DependencyType.COMPILE_TIME));
      return this;
    }

    public Builder addRuntimeDep(String s) {
      return addRuntimeDep(Label.create(s));
    }

    public Builder addRuntimeDep(Label label) {
      this.dependencies.add(
          new Dependency(TargetKey.forPlainTarget(label), DependencyType.RUNTIME));
      return this;
    }

    public TargetIdeInfo build() {
      return new TargetIdeInfo(
          key,
          kind,
          buildFile,
          dependencies,
          tags,
          sources,
          cIdeInfo,
          cToolchainIdeInfo,
          javaIdeInfo,
          androidIdeInfo,
          null,
          androidAarIdeInfo,
          pyIdeInfo,
          goIdeInfo,
          jsIdeInfo,
          tsIdeInfo,
          dartIdeInfo,
          testIdeInfo,
          javaToolchainIdeInfo,
          kotlinToolchainIdeInfo);
    }
  }
}
